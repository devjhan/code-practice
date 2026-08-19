package leetcode.p0912_sort_an_array;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class QuickSortWithDutchPartitionTest {
    static class Solution {
        private static void swap(int[] nums, int frontIndex, int rearIndex) {
            int temp = nums[frontIndex];
            nums[frontIndex] = nums[rearIndex];
            nums[rearIndex] = temp;
        }

        private static void quickSort(int[] nums, int startIndex, int endIndex) {
            if (startIndex >= endIndex) {
                return;
            }
            int pivotIndex = startIndex + (int) (Math.random() * (endIndex - startIndex + 1));
            int pivotValue = nums[pivotIndex];

            int lesserThanIndex = startIndex;
            int greaterThanIndex = endIndex;
            int i = startIndex;

            while (i <= greaterThanIndex) {
                if (nums[i] < pivotValue) {
                    swap(nums, i++, lesserThanIndex++);
                } else if (nums[i] > pivotValue) {
                    swap(nums, i, greaterThanIndex--);
                } else {
                    ++i;
                }
            }

            quickSort(nums, startIndex, lesserThanIndex - 1);
            quickSort(nums, greaterThanIndex + 1, endIndex);
        }

        public int[] sortArray(int[] nums) {
            quickSort(nums, 0, nums.length - 1);
            return nums;
        }
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new int[] {5, 2, 3, 1}, new int[] {1, 2, 3, 5}),
                Arguments.of(new int[] {5, 1, 1, 2, 0, 0}, new int[] {0, 0, 1, 1, 2, 5}));
    }

    @ParameterizedTest
    @MethodSource("cases")
    void sortArray(int[] nums, int[] expected) {
        assertThat(new Solution().sortArray(nums)).isEqualTo(expected);
    }
}
