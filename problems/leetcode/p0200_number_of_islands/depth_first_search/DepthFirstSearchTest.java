package leetcode.p0200_number_of_islands.depth_first_search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static final int[][] deltas = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private void dfs(char[][] grid, int[] start) {
        grid[start[0]][start[1]] = '2';

        for (int[] delta : deltas) {
            int nx = start[0] + delta[0], ny = start[1] + delta[1];

            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && grid[nx][ny] == '1') {
                dfs(grid, new int[]{nx, ny});
            }
        }
    }
    public int numIslands(char[][] grid) {
        int islandsCount = 0;

        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] == '1') {
                    dfs(grid, new int[]{i, j});
                    ++islandsCount;
                }
            }
        }
        return islandsCount;
    }
}

class DepthFirstSearchTest {
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
