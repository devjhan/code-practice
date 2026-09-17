package leetcode.p0097_interleaving_string.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() < 1 && s2.length() < 1) return s3.length() < 1;
        else if (s1.length() < 1) return s3.equals(s2);
        else if (s2.length() < 1) return s3.equals(s1);
        else if (s1.length() + s2.length() != s3.length()) return false;

        boolean[] dp = new boolean[s1.length() + 1];
        dp[0] = true;

        for (int i = 1; i < dp.length; i++) {
            dp[i] = dp[i - 1] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }

        for (int i = 1; i <= s2.length(); i++) {
            dp[0] = dp[0] && s2.charAt(i - 1) == s3.charAt(i - 1);
            for (int j = 1; j <= s1.length(); j++) {
                dp[j] = (dp[j] && s2.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[j - 1] && s1.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }

        return dp[s1.length()];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of("aabcc", "dbbca", "aadbbcbcac", true),
            Arguments.of("aabcc", "dbbca", "aadbbbaccc", false),
            Arguments.of("", "", "", true),
            Arguments.of("", "", "a", false),
            Arguments.of("a", "b", "a", false),
            Arguments.of("aacaac", "aacaaeaac", "aacaaeaaeaacaac", false)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void isInterleave(String s1, String s2, String s3, boolean expected) {
        assertThat(new Solution().isInterleave(s1, s2, s3)).isEqualTo(expected);
    }
}
