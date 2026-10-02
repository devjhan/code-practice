package leetcode.p1293_shortest_path_in_a_grid_with_obstacles_elimination.breadth_first_search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static final int[][] deltas = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public int shortestPath(int[][] grid, int k) {
        Deque<int[]> q = new ArrayDeque<>();
        int[][] leftBudgets = new int[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; ++i) Arrays.fill(leftBudgets[i], -1);
        leftBudgets[0][0] = k;

        q.offer(new int[] {0, 0, 0, k});

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int i = curr[0], j = curr[1], accDist = curr[2], leftBudget = curr[3];

            if (i == grid.length - 1 && j == grid[0].length - 1) return accDist;

            for (int[] delta: deltas) {
                int ni = i + delta[0], nj = j + delta[1];

                if (ni < 0 || ni >= grid.length || nj < 0 || nj >= grid[0].length) {
                    continue;
                }

                int nextLeftBudget = leftBudget - grid[ni][nj];

                if (nextLeftBudget >= 0 && nextLeftBudget > leftBudgets[ni][nj]) {
                    leftBudgets[ni][nj] = nextLeftBudget;
                    q.offer(new int[] {ni, nj, accDist + 1, nextLeftBudget});
                }
            }
        }
        return -1;
    }
}

class BreadthFirstSearchTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][] {{0, 0, 0}, {1, 1, 0}, {0, 0, 0}, {0, 1, 1}, {0, 0, 0}}, 1, 6),
            Arguments.of(new int[][] {{0, 1, 1}, {1, 1, 1}, {1, 0, 0}}, 1, -1),
            Arguments.of(new int[][] {{0, 1}, {1, 0}}, 0, -1),
            Arguments.of(new int[][] {{0, 1}, {1, 0}}, 1, 2),
            Arguments.of(new int[][] {{0, 1, 1}, {1, 0, 1}, {1, 1, 0}}, 2, 4),
            Arguments.of(new int[][] {
                {0, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {0, 0},
                {0, 1},
                {0, 1},
                {0, 1},
                {0, 0},
                {1, 0},
                {1, 0},
                {0, 0}}, 4, 14),
            Arguments.of(new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 0, 1, 1, 1, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}}, 1, 20)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void shortestPath(int[][] grid, int k, int expected) {
        assertThat(new Solution().shortestPath(grid, k)).isEqualTo(expected);
    }
}
