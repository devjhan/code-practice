package leetcode.p0001_two_sum;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();

        for (int index = 0; index < nums.length; index++) {
            int complement = target - nums[index];
            if (seen.containsKey(complement)) {
                return new int[] {seen.get(complement), index};
            }
            seen.put(nums[index], index);
        }

        return new int[0];
    }
}

class TwoSumTest {
    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new int[] {2, 7, 11, 15}, 9, new int[] {0, 1}),
                Arguments.of(new int[] {3, 2, 4}, 6, new int[] {1, 2}),
                Arguments.of(new int[] {3, 3}, 6, new int[] {0, 1}));
    }

    @ParameterizedTest
    @MethodSource("cases")
    void twoSum(int[] nums, int target, int[] expected) {
        assertThat(new Solution().twoSum(nums, target)).isEqualTo(expected);
    }
}
