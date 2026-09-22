package leetcode.p0931_minimum_falling_path_sum.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int[] dp = matrix[0].clone();
        final int endIndex = matrix[0].length - 1;

        for (int i = 1; i < matrix.length; ++i) {
            int leftDiag = dp[0];
            dp[0] = matrix[i][0] + Math.min(dp[0], dp[1]);

            for (int j = 1; j < endIndex; ++j) {
                int temp = dp[j];
                dp[j] = matrix[i][j] + Math.min(leftDiag, Math.min(dp[j], dp[j + 1]));
                leftDiag = temp;
            }
            dp[endIndex] = matrix[i][endIndex] + Math.min(leftDiag, dp[endIndex]);
        }
        return Arrays.stream(dp).min().getAsInt();
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][] {{2, 1, 3}, {6, 5, 4}, {7, 8, 9}}, 13),
            Arguments.of(new int[][] {{-19, 57}, {-40, -5}}, -59),
            Arguments.of(new int[][] {{100, -42, -46, -41}, {31, 97, 10, -10}, {-58, -51, 82, 89}, {51, 81, 69, -51}}, -36)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void minFallingPathSum(int[][] matrix, int expected) {
        assertThat(new Solution().minFallingPathSum(matrix)).isEqualTo(expected);
    }
}
