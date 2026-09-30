package leetcode.p0078_subsets.bit_mask;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        for (int mask = 0; mask < (1 << nums.length); ++mask) {
            List<Integer> subset = new ArrayList<>();

            for (int i = 0; i < nums.length; ++i) {
                if ((mask & (1 << i)) != 0) subset.add(nums[i]);
            }
            result.add(subset);
        }
        return result;
    }
}

class BitMaskTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{1, 2, 3}, List.of(
                List.of(),
                List.of(1),
                List.of(2),
                List.of(3),
                List.of(1, 2),
                List.of(1, 3),
                List.of(2, 3),
                List.of(1, 2, 3)
            )),
            Arguments.of(new int[]{0}, List.of(
                List.of(0),
                List.of()
            ))
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void subsets(int[] nums, List<List<Integer>> expected) {
        assertThat(new Solution().subsets(nums)).containsExactlyInAnyOrderElementsOf(expected);
    }
}
