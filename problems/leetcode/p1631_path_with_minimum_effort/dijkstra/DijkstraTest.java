package leetcode.p1631_path_with_minimum_effort.dijkstra;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static record State(int accEffort, int i, int j) {}
    public int minimumEffortPath(int[][] heights) {
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(state -> state.accEffort()));
        int[][] effort = new int[heights.length][heights[0].length];

        Arrays.stream(effort).forEach(row -> Arrays.fill(row, Integer.MAX_VALUE));
        effort[0][0] = 0;

        pq.offer(new State(0, 0, 0));

        while(!pq.isEmpty()) {
            State curr = pq.poll();

            if (curr.accEffort() > effort[curr.i()][curr.j()]) {
                continue;
            }

            for (int[] delta: deltas) {
                int ni = curr.i() + delta[0], nj = curr.j() + delta[1];

                if (ni < 0 || ni >= heights.length || nj < 0 || nj >= heights[curr.i()].length) {
                    continue;
                }

                if (effort[ni][nj] > Math.max(curr.accEffort(), Math.abs(heights[ni][nj] - heights[curr.i()][curr.j()]))) {
                    effort[ni][nj] = Math.max(curr.accEffort(), Math.abs(heights[ni][nj] - heights[curr.i()][curr.j()]));
                    pq.offer(new State(effort[ni][nj], ni, nj));
                }
            }
        }
        return effort[heights.length - 1][heights[0].length - 1];
    }
}

class DijkstraTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][]{{1, 2, 2}, {3, 8, 2}, {5, 3, 5}}, 2),
            Arguments.of(new int[][]{{1, 2, 3}, {3, 8, 4}, {5, 3, 5}}, 1),
            Arguments.of(new int[][]{{1, 2, 1, 1, 1}, {1, 2, 1, 2, 1}, {1, 2, 1, 2, 1}, {1, 2, 1, 2, 1}, {1, 1, 1, 2, 1}}, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void minimumEffortPath(int[][] heights, int expected) {
        assertThat(new Solution().minimumEffortPath(heights)).isEqualTo(expected);
    }
}
