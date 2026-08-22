package leetcode.p0200_number_of_islands.breadth_first_search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final char WATER = '0';
    private static final char GROUND = '1';
    private static final char CHECKED_GROUND = '2';

    public int numIslands(char[][] grid) {
        Deque<Integer[]> queue = new ArrayDeque<>();
        int islandCount = 0;

        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] != WATER) {
                    if (grid[i][j] == GROUND) {
                        grid[i][j] = CHECKED_GROUND;
                        ++islandCount;
                    }
                    queue.offer(new Integer[]{i, j});

                    while (!queue.isEmpty()) {
                        Integer[] pos = queue.poll();

                        for (int[] delta: deltas) {
                            int ni = pos[0] + delta[0], nj = pos[1] + delta[1];

                            if (ni < 0 || ni >= grid.length || nj < 0 || nj >= grid[ni].length) {
                                continue;
                            }

                            if (grid[ni][nj] == GROUND) {
                                grid[ni][nj] = CHECKED_GROUND;
                                queue.offer(new Integer[]{ni, nj});
                            }
                        }
                    }
                }
            }
        }
        return islandCount;
    }
}

class BreadthFirstSearchTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new char[][] {{'1', '1', '1', '1', '0'}, {'1', '1', '0', '1', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '0', '0', '0'}}, 1),
            Arguments.of(new char[][] {{'1', '1', '0', '0', '0'}, {'1', '1', '0', '0', '0', '0'}, {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}}, 3),
            Arguments.of(new char[][] {{'1', '1', '1'}, {'0', '1', '0'}, {'1', '1', '1'}}, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void numIslands(char[][] grid, int expected) {
        assertThat(new Solution().numIslands(grid)).isEqualTo(expected);
    }
}
