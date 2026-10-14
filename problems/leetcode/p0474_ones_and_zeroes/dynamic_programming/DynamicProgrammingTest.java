package leetcode.p0474_ones_and_zeroes.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static int count(String s, char ch) {
        int count = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == ch) ++count;
        }
        return count;
    }
    public int findMaxForm(String[] strs, int m, int n) {
        int[] zeroes = Arrays.stream(strs).mapToInt(s -> count(s, '0')).toArray();
        int[] ones = Arrays.stream(strs).mapToInt(s -> count(s, '1')).toArray();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i < strs.length; ++i) {
            for (int j = m; j >= zeroes[i]; --j) {
                for (int k = n; k >= ones[i]; --k) {
                    dp[j][k] = Math.max(dp[j][k], dp[j - zeroes[i]][k - ones[i]] + 1);
                }
            }
        }
        return dp[m][n];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new String[]{"10", "0001", "111001", "1", "0"}, 5, 3, 4),
            Arguments.of(new String[]{"10", "0", "1"}, 1, 1, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void findMaxForm(String[] strs, int m, int n, int expected) {
        assertThat(new Solution().findMaxForm(strs, m, n)).isEqualTo(expected);
    }
}
