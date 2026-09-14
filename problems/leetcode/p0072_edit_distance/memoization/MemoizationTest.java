package leetcode.p0072_edit_distance.memoization;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static int dfs(final String word1, final String word2, int i, int j, int[][] memo) {
        if (i == word1.length()) return word2.length() - j;
        if (j == word2.length()) return word1.length() - i;
        if (memo[i][j] != -1) return memo[i][j];

        if (word1.charAt(i) == word2.charAt(j)) {
            return memo[i][j] = dfs(word1, word2, i + 1, j + 1, memo);
        }

        return memo[i][j] = 1 + Math.min(
            dfs(word1, word2, i + 1, j, memo), // delete
            Math.min(
                dfs(word1, word2, i, j + 1, memo), // insertion
                dfs(word1, word2, i + 1, j + 1, memo) // replace
            )
        );
    }
    public int minDistance(String word1, String word2) {
        int[][] memo = new int[word1.length()][word2.length()];
        for (int[] row : memo) Arrays.fill(row, -1);

        return dfs(word1, word2, 0, 0, memo);
    }
}

class MemoizationTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of("horse", "ros", 3),
            Arguments.of("intention", "execution", 5)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void minDistance(String word1, String word2, int expected) {
        assertThat(new Solution().minDistance(word1, word2)).isEqualTo(expected);
    }
}
