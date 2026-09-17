package leetcode.p0115_distinct_subsequences.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int numDistinct(String s, String t) {
        int[] dp = new int[t.length() + 1];
        dp[0] = 1;

        for (int i = 1; i <= s.length(); ++i) {
            for (int j = t.length(); j >= 1; --j) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }
        return dp[t.length()];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of("rabbbit", "rabbit", 3),
            Arguments.of("babgbag", "bag", 5),
            Arguments.of("t", "t", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void numDistinct(String s, String t, int expected) {
        assertThat(new Solution().numDistinct(s, t)).isEqualTo(expected);
    }
}
