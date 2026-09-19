package leetcode.p0516_longest_palindromic_subsequence.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int longestPalindromeSubseq(String s) {
        int[] dp = new int[s.length() + 1];

        for (int i = s.length() - 1; i >= 0; --i) {
            int diag = 0;

            for (int j = i + 1; j <= s.length(); ++j) {
                int temp = dp[j];

                if (j == i + 1) dp[j] = 1;
                else if (s.charAt(i) == s.charAt(j - 1)) dp[j] = diag + 2;
                else dp[j] = Math.max(dp[j], dp[j - 1]);

                diag = temp;
            }
        }
        return dp[s.length()];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of("bbbab", 4),
            Arguments.of("cbbd", 2)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void longestPalindromeSubseq(String s, int expected) {
        assertThat(new Solution().longestPalindromeSubseq(s)).isEqualTo(expected);
    }
}
