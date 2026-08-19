package leetcode.p0215_kth_largest_element_in_an_array;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class QuickSelectTest {
    static class Solution {
        private static void swap(int[] nums, int a, int b) {
            int temp = nums[a];
            nums[a] = nums[b];
            nums[b] = temp;
        }

        private int quickSelect(int[] nums, int startIndex, int endIndex, int k) {
            int pivotIndex = (int) (Math.random() * (endIndex - startIndex + 1) + startIndex);
            int pivotValue = nums[pivotIndex];

            int lesserIndex = startIndex, greaterIndex = endIndex;
            int current = startIndex;

            while (current <= greaterIndex) {
                if (nums[current] < pivotValue) {
                    swap(nums, lesserIndex++, current++);
                } else if (nums[current] > pivotValue) {
                    swap(nums, greaterIndex--, current);
                } else {
                    ++current;
                }
            }

            if (k >= lesserIndex && k <= greaterIndex) {
                return nums[k];
            } else if (k < lesserIndex) {
                return quickSelect(nums, startIndex, lesserIndex - 1, k);
            } else {
                return quickSelect(nums, greaterIndex + 1, endIndex, k);
            }
        }

        public int findKthLargest(int[] nums, int k) {
            return quickSelect(nums, 0, nums.length - 1, nums.length - k);
        }
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new int[] {3, 2, 1, 5, 6, 4}, 2, 5),
                Arguments.of(new int[] {3, 2, 3, 1, 2, 4, 5, 5, 6}, 4, 4));
    }

    @ParameterizedTest
    @MethodSource("cases")
    void findKthLargest(int[] nums, int k, int expected) {
        assertThat(new Solution().findKthLargest(nums, k)).isEqualTo(expected);
    }
}
