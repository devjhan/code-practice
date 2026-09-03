package leetcode.p0084_largest_rectangle_in_histogram.monotonic_stack;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int largestRectangleArea(int[] heights) {
        int[] stack = new int[heights.length + 1];
        stack[0] = 0;
        int top = 0;

        heights = Arrays.copyOf(heights, heights.length + 1);

        int maxArea = 0;

        for (int i = 1; i < heights.length; ++i) {
            if (heights[stack[top]] <= heights[i]) {
                stack[++top] = i;
            } else {
                while (top > -1 && heights[stack[top]] > heights[i]) {
                    int height = heights[stack[top--]];
                    int width = (top == -1) ? i : i - stack[top] - 1;
                    maxArea = Math.max(maxArea, width * height);
                }
                stack[++top] = i;
            }
        }
        return maxArea;
    }
}

class MonotonicStackTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{2, 1, 5, 6, 2, 3}, 10),
            Arguments.of(new int[]{2, 4}, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void largestRectangleArea(int[] heights, int expected) {
        assertThat(new Solution().largestRectangleArea(heights)).isEqualTo(expected);
    }
}
