package leetcode.p0983_minimum_cost_for_tickets.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int mincostTickets(int[] days, int[] costs) {
        int[] dp = new int[days[days.length - 1] + 1];
        int daysPtr = 0;

        for (int i = 1; i <= days[days.length - 1]; i++) {
            if (days[daysPtr] == i) {
                dp[i] = Math.min(Math.min(dp[i - 1] + costs[0], dp[Math.max(i - 7, 0)] + costs[1]), dp[Math.max(i - 30, 0)] + costs[2]);
                ++daysPtr;
            } else {
                dp[i] = dp[i - 1];
            }
        }
        return dp[dp.length - 1];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{1, 4, 6, 7, 8, 20}, new int[]{2, 7, 15}, 11),
            Arguments.of(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 31}, new int[]{2, 7, 15}, 17)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void mincostTickets(int[] days, int[] costs, int expected) {
        assertThat(new Solution().mincostTickets(days, costs)).isEqualTo(expected);
    }
}
