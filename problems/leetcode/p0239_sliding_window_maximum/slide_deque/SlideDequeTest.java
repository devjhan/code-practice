package leetcode.p0239_sliding_window_maximum.slide_deque;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> dq = new ArrayDeque<>(k);
        int[] maxPerWindow = new int[nums.length - k + 1];

        for (int i = 0; i < nums.length; ++i) {
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.removeLast();
            while (!dq.isEmpty() && dq.peekFirst() < i - k + 1) dq.removeFirst();
            dq.addLast(i);

            if (i >= k - 1) maxPerWindow[i - k + 1] = nums[dq.peekFirst()];
        }
        return maxPerWindow;
    }
}

class SlideDequeTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3, new int[]{3, 3, 5, 5, 6, 7}),
            Arguments.of(new int[]{1}, 1, new int[] {1})
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void maxSlidingWindow(int[] nums, int k, int[] expected) {
        assertThat(new Solution().maxSlidingWindow(nums, k)).isEqualTo(expected);
    }
}
