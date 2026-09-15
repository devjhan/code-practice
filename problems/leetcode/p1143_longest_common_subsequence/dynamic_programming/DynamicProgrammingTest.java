package leetcode.p1143_longest_common_subsequence.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int[] dp = new int[text1.length() + 1];

        for (int i = text2.length() - 1; i >= 0; i--) {
            int fromDiag = 0;

            for (int j = text1.length() - 1; j >= 0; j--) {
                int temp = dp[j];

                if (text1.charAt(j) == text2.charAt(i)) dp[j] = fromDiag + 1;
                else dp[j] = Math.max(dp[j + 1], dp[j]);

                fromDiag = temp;
            }
        }
        return dp[0];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of("abcde", "ace", 3),
            Arguments.of("abc", "abc", 3),
            Arguments.of("abc", "def", 0)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void longestCommonSubsequence(String text1, String text2, int expected) {
        assertThat(new Solution().longestCommonSubsequence(text1, text2)).isEqualTo(expected);
    }
}
