package leetcode.p0279_perfect_squares.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 0; i < dp.length; ++i) {
            for (int sq = 1; sq <= Math.sqrt(n - i); ++sq) {
                dp[i + sq * sq] = Math.min(dp[i + sq * sq], dp[i] + 1);
            }
        }
        return dp[n];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(12, 3),
            Arguments.of(13, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void numSquares(int n, int expected) {
        assertThat(new Solution().numSquares(n)).isEqualTo(expected);
    }
}
