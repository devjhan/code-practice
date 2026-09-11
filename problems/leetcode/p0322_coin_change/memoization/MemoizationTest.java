package leetcode.p0322_coin_change.memoization;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static final long INF = Long.MAX_VALUE - 1;
    private static final int UNVISITED = -2;
    private static final int UNVISITABLE = -1;

    private static long dfs(int amount, final int[] coins, int[] memo) {
        if (amount == 0) return 0;
        if (amount < 0) return INF;
        if (memo[amount] != UNVISITED) return memo[amount];

        long result = INF;

        for (int coin: coins) {
            long temp = dfs(amount - coin, coins, memo);
            if (temp != UNVISITABLE) result = Math.min(result, temp + 1);
        }
        memo[amount] = (result == INF ? UNVISITABLE : (int) result);
        return result;
    }
    public int coinChange(int[] coins, int amount) {
        int[] memo = IntStream.generate(() -> UNVISITED).limit(amount + 1).toArray();
        memo[0] = 0;

        final long result = dfs(amount, coins, memo);
        return (int) (result == INF ? -1 : result);
    }
}

class MemoizationTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{1, 2, 5}, 11, 3),
            Arguments.of(new int[]{2}, 3, -1),
            Arguments.of(new int[]{1}, 0, 0),
            Arguments.of(new int[]{1, 2, 5}, 100, 20),
            Arguments.of(new int[]{186, 419, 83, 408}, 6249, 20)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void coinChange(int[] coins, int amount, int expected) {
        assertThat(new Solution().coinChange(coins, amount)).isEqualTo(expected);
    }
}
