package leetcode.p0787_cheapest_flights_within_k_steps.dijkstra;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<int[]>[] adjacencyList = new ArrayList[n];
        for (int i = 0; i < n; ++i) adjacencyList[i] = new ArrayList<>();
        for (int[] edge: flights) adjacencyList[edge[0]].add(new int[] {edge[1], edge[2]});
        // from -> to, cost

        int[][] minDist = new int[n][k + 2];
        for (int i = 0; i < n; ++i) Arrays.fill(minDist[i], Integer.MAX_VALUE);
        minDist[src][k + 1] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        pq.offer(new int[] {0, src, k + 1});
        // cost, curr, counter

        while(!pq.isEmpty()) {
            int[] temp = pq.poll();

            final int accDist = temp[0];
            final int curr = temp[1];
            final int remained = temp[2];

            if (curr == dst) return accDist;
            if (remained == 0 || accDist > minDist[curr][remained]) continue;

            for (int[] edge: adjacencyList[curr]) {
                final int to = edge[0];
                final int cost = edge[1];

                if (cost + accDist < minDist[to][remained - 1]) {
                    minDist[to][remained - 1] = cost + accDist;
                    pq.offer(new int[] {cost + accDist, to, remained - 1});
                }
            }
        }
        return -1;
    }
}

class DijkstraTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(4, new int[][]{{0, 1, 100}, {1, 2, 100}, {2, 0, 100}, {1, 3, 600}, {2, 3, 200}}, 0, 3, 1, 700),
            Arguments.of(3, new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}}, 0, 2, 1, 200),
            Arguments.of(3, new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}}, 0, 2, 0, 500)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void findCheapestPrice(int n, int[][] flights, int src, int dst, int k, int expected) {
        assertThat(new Solution().findCheapestPrice(n, flights, src, dst, k)).isEqualTo(expected);
    }
}
