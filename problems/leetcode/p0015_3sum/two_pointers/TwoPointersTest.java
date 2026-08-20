package leetcode.p0015_3sum.two_pointers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> answer = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; ++i) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;

            while (left < right) {
                if (target == nums[left] + nums[right]) {
                    answer.add(List.of(nums[i], nums[left], nums[right]));

                    while (left < right && nums[right - 1] == nums[right]) {
                        --right;
                    }
                    --right;
                } else if (target < nums[left] + nums[right]) {
                    while (left < right && nums[right - 1] == nums[right]) {
                        --right;
                    }
                    --right;
                } else {
                    while (left < right && nums[left + 1] == nums[left]) {
                        ++left;
                    }
                    ++left;
                }
            }
        }
        return answer;
    }
}

class TwoPointersTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[] {0, 1, 1}, new ArrayList<List<Integer>>()),
            Arguments.of(new int[] {0, 0, 0}, new ArrayList<List<Integer>>(List.of(List.of(0, 0, 0)))),
            Arguments.of(new int[] {-1, 0, 1, 2, -1, -4}, new ArrayList<List<Integer>>(List.of(List.of(-1, -1, 2), List.of(-1, 0, 1))))
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void threeSum(int[] nums, List<List<Integer>> expected) {
        assertThat(new Solution().threeSum(nums)).containsExactlyInAnyOrderElementsOf(expected);
    }
}
