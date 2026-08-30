package leetcode.p0416_partition_equal_subset_sum.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();

        if (sum % 2 != 0) return false;

        int target = sum / 2;
        BitSet sumExists = new BitSet(target + 1);

        for (int i = 0; i < nums.length; ++i) {
            BitSet deltas = new BitSet(target + 1);

            if (nums[i] < sumExists.size()) {
                deltas.set(nums[i]);
            }

            for (int j = 1; j < sumExists.size(); ++j) {
                if (sumExists.get(j) && nums[i] + j < sumExists.size()) {
                    deltas.set(nums[i] + j);
                }
            }
            sumExists.or(deltas);
        }
        return sumExists.get(target);
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[] {1, 5, 11, 5}, true),
            Arguments.of(new int[] {1, 2, 3, 5}, false),
            Arguments.of(new int[] {1, 2, 5}, false),
            Arguments.of(new int[] {2, 2, 3, 5}, false),
            Arguments.of(new int[] {20, 9, 10, 18, 14, 4, 15, 19, 19}, true)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void canPartition(int[] nums, boolean expected) {
        assertThat(new Solution().canPartition(nums)).isEqualTo(expected);
    }
}
