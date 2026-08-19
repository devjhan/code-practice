package leetcode.p0912_sort_an_array.heap_sort;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static void swap(int[] nums, int frontIndex, int rearIndex) {
        int temp = nums[frontIndex];
        nums[frontIndex] = nums[rearIndex];
        nums[rearIndex] = temp;
    }

    private static int getLeftChild(int index) {
        return index * 2 + 1;
    }

    private static int getRightChild(int index) {
        return index * 2 + 2;
    }

    private void siftDown(int[] nums, int index, int heapSize) {
        while (getLeftChild(index) < heapSize) {
            int leftIndex = getLeftChild(index), rightIndex = getRightChild(index);
            int largerIndex = leftIndex;

            if (rightIndex < heapSize && nums[leftIndex] < nums[rightIndex]) {
                largerIndex = rightIndex;
            }

            if (nums[index] < nums[largerIndex]) {
                swap(nums, index, largerIndex);
                index = largerIndex;
            } else {
                break;
            }
        }
    }

    private void heapify(int[] nums) {
        for (int i = nums.length / 2 - 1; i >= 0; --i) {
            siftDown(nums, i, nums.length);
        }
    }

    private void heapSort(int[] nums) {
        heapify(nums);
        for (int i = nums.length - 1; i > 0; --i) {
            swap(nums, 0, i);
            siftDown(nums, 0, i);
        }
    }

    public int[] sortArray(int[] nums) {
        heapSort(nums);
        return nums;
    }
}

class HeapSortTest {
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
