package leetcode.p0139_work_break.memoization;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static boolean dfs(String s, int i, Boolean[] memo, final int[][] hashLenArray) {
        if (i == s.length()) return true;
        if (memo[i] != null) return memo[i];

        for (int[] hashLen : hashLenArray) {
            if (i + hashLen[1] <= s.length() && s.substring(i, i + hashLen[1]).hashCode() == hashLen[0] && dfs(s, i + hashLen[1], memo, hashLenArray)) {
                return memo[i] = true;
            }
        }
        return memo[i] = false;
    }
    public boolean wordBreak(String s, List<String> wordDict) {
        int[][] hashLenArray = wordDict.stream().map(str -> new int[]{str.hashCode(), str.length()}).toArray(int[][]::new);
        return dfs(s, 0, new Boolean[s.length()], hashLenArray);
    }
}

class MemoizationTest {
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
