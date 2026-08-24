package leetcode.p1368_minimum_cost_to_make_at_least_one_valid_path_in_a_grid.zero_one_breadth_first_search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    int[][] deltas = {{4, -1, 0}, {3, 1, 0}, {2, 0, -1}, {1, 0, 1}};
    public int minCost(int[][] grid) {
        int[][] dist = new int[grid.length][grid[0].length];
        Deque<int[]> q = new ArrayDeque<>();

        q.addFirst(new int[]{0, 0});

        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE - 1);
        }
        dist[0][0] = 0;

        while (!q.isEmpty()) {
            int[] curr = q.removeFirst();
            int i = curr[0], j = curr[1];

            int[] dir = Arrays.stream(deltas).filter(t -> t[0] == grid[i][j]).findAny().get();

            if (i + dir[1] >= 0 && i + dir[1] < grid.length && j + dir[2] >= 0 && j + dir[2] < grid[0].length) {
                if (dist[i + dir[1]][j + dir[2]] > dist[i][j]) {
                    dist[i + dir[1]][j + dir[2]] = dist[i][j];
                    q.addFirst(new int[]{i + dir[1], j + dir[2]});
                }
            }

            for (int[] delta: deltas) {
                if (i + delta[1] >= 0 && i + delta[1] < grid.length && j + delta[2] >= 0 && j + delta[2] < grid[0].length) {
                    if (dist[i + delta[1]][j + delta[2]] > dist[i][j] + 1) {
                        dist[i + delta[1]][j + delta[2]] = dist[i][j] + 1;
                        q.addLast(new int[]{i + delta[1], j + delta[2]});
                    }
                }
            }

        }
        return dist[grid.length - 1][grid[0].length - 1];
    }
}

class ZeroOneBreadthFirstSearchTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][]{{1, 1, 1, 1}, {2, 2, 2, 2}, {1, 1, 1, 1}, {2, 2, 2, 2}}, 3),
            Arguments.of(new int[][]{{1, 1, 3}, {3, 2, 2}, {1, 1, 4}}, 0),
            Arguments.of(new int[][]{{1, 2}, {4, 3}}, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void minCost(int[][] grid, int expected) {
        assertThat(new Solution().minCost(grid)).isEqualTo(expected);
    }
}
