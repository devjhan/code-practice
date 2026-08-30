package leetcode.p0300_longest_increasing_subsequence.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        for (int i = 1; i < dp.length; ++i) {
            for (int j = 0; j < i; ++j) {
                dp[i] = (nums[i] > nums[j]) ? Math.max(dp[i], dp[j] + 1) : dp[i];
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[] {1}, 1),
            Arguments.of(new int[] {10, 9, 2, 5, 3, 7, 101, 18}, 4),
            Arguments.of(new int[] {0, 1, 0, 3, 2, 3}, 4),
            Arguments.of(new int[] {7, 7, 7, 7, 7, 7, 7}, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void lengthOfLIS(int[] nums, int expected) {
        assertThat(new Solution().lengthOfLIS(nums)).isEqualTo(expected);
    }
}
