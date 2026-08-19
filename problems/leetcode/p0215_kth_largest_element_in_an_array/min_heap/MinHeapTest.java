package leetcode.p0215_kth_largest_element_in_an_array.min_heap;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.PriorityQueue;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int i = 0; i < k; ++i) {
            minHeap.add(nums[i]);
        }

        for (int i = k; i < nums.length; ++i) {
            if (minHeap.peek() < nums[i]) {
                minHeap.add(nums[i]);
                minHeap.poll();
            }
        }
        return minHeap.peek();
    }
}

class MinHeapTest {
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
