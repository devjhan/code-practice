package leetcode.p0221_maximal_square.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int maximalSquare(char[][] matrix) {
        int[] dp = new int[matrix[0].length];
        int maxLen = 0;

        for (int i = 0; i < matrix.length; ++i) {
            int diag = dp[0];
            dp[0] = matrix[i][0] - '0';

            for (int j = 1; j < matrix[i].length; ++j) {
                int temp = dp[j];

                if (matrix[i][j] == '1') dp[j] = 1 + Math.min(dp[j - 1], Math.min(dp[j], diag));
                else dp[j] = 0;
                diag = temp;
            }
            maxLen = Math.max(maxLen, Arrays.stream(dp).max().getAsInt());
        }
        return maxLen * maxLen;
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new char[][]{{'1'}}, 1),
            Arguments.of(new char[][]{{'0'}}, 0),
            Arguments.of(new char[][]{{'1', '1'}}, 1),
            Arguments.of(new char[][]{{'1', '1'}, {'1', '1'}}, 4),
            Arguments.of(new char[][]{{'1', '1'}, {'1', '0'}}, 1),
            Arguments.of(new char[][]{{'1', '0'}, {'1', '0'}}, 1),
            Arguments.of(new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}}, 4),
            Arguments.of(new char[][]{{'1', '0'}, {'0', '1'}, {'0', '1'}, {'0', '1'}, {'1', '1'}, {'0', '0'}, {'0', '1'}}, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void maximalSquare(char[][] matrix, int expected) {
        assertThat(new Solution().maximalSquare(matrix)).isEqualTo(expected);
    }
}
