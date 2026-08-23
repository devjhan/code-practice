package leetcode.p0743_network_delay_time.dijkstra;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static record Edge(int cost, int vertex) {}
    public int networkDelayTime(int[][] times, int n, int k) {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.cost()));
        int[] dist = new int[n];
        List<Edge>[] adjList = new ArrayList[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k - 1] = 0;

        for (int i = 0; i < n; ++i) {
            adjList[i] = new ArrayList<>();
        }

        for (int[] edge: times) {
            adjList[edge[0] - 1].add(new Edge(edge[2], edge[1] - 1));
        }

        pq.offer(new Edge(0, k - 1));

        while(!pq.isEmpty()) {
            Edge via = pq.poll();

            for (Edge to: adjList[via.vertex()]) {
                int nextCost = via.cost() + to.cost();

                if (dist[to.vertex()] > nextCost) {
                    dist[to.vertex()] = nextCost;
                    pq.offer(new Edge(dist[to.vertex()], to.vertex()));
                }
            }
        }
        int max = Arrays.stream(dist).max().getAsInt();
        return max == Integer.MAX_VALUE ? -1 : max;
    }
}

class DijkstraTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][]{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, 4, 2, 2),
            Arguments.of(new int[][]{{1, 2, 1}}, 2, 1, 1),
            Arguments.of(new int[][]{{1, 2, 1}}, 2, 2, -1)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void networkDelayTime(int[][] times, int n, int k, int expected) {
        assertThat(new Solution().networkDelayTime(times, n, k)).isEqualTo(expected);
    }
}
