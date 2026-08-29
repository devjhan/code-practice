package leetcode.p0064_minimum_path_sum.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int minPathSum(int[][] grid) {
        int[] dp = new int[grid[0].length];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 0; i < grid.length; i++) {
            dp[0] = dp[0] + grid[i][0];

            for (int j = 1; j < grid[0].length; ++j) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + grid[i][j];
            }
        }
        return dp[dp.length - 1];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}, 7),
            Arguments.of(new int[][]{{1, 2, 3}, {4, 5, 6}}, 12)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void minPathSum(int[][] grid, int expected) {
        assertThat(new Solution().minPathSum(grid)).isEqualTo(expected);
    }
}
