package leetcode.p0139_work_break.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 0; i <= s.length(); ++i) {
            if (!dp[i]) continue;

            for (String word: wordDict) {
                int end = i + word.length();
                if (end <= s.length() && dp[i] && s.substring(i, end).equals(word)) {
                    dp[end] = true;
                }
            }
        }
        return dp[s.length()];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of("leetcode", Arrays.asList("leet", "code"), true),
            Arguments.of("applepenapple", Arrays.asList("apple", "pen"), true),
            Arguments.of("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat"), false)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void wordBreak(String s, List<String> wordDict, boolean expected) {
        assertThat(new Solution().wordBreak(s, wordDict)).isEqualTo(expected);
    }
}
