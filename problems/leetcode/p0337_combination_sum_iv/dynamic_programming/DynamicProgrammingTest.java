package leetcode.p0337_combination_sum_iv.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int i = 1; i <= target; ++i) {
            for (int j = 0; j < nums.length; ++j) {
                if (i - nums[j] >= 0) dp[i] += dp[i - nums[j]];
            }
        }
        return dp[target];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[] {1, 2, 3}, 4, 7)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void combinationSum4(int[] nums, int target, int expected) {
        assertThat(new Solution().combinationSum4(nums, target)).isEqualTo(expected);
    }
}
