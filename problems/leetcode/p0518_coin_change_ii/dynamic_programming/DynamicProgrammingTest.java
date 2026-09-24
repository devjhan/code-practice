package leetcode.p0518_coin_change_ii.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin: coins) {
            for (int i = coin; i <= amount; ++i) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(5, new int[] {1, 2, 5}, 4),
            Arguments.of(3, new int[] {2}, 0),
            Arguments.of(10, new int[] {10}, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void change(int amount, int[] coins, int expected) {
        assertThat(new Solution().change(amount, coins)).isEqualTo(expected);
    }
}
