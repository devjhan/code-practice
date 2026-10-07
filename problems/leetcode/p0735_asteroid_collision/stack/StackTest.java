package leetcode.p0735_asteroid_collision.stack;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (int ast : asteroids) {
            boolean isColliding = false;

            while (!stack.isEmpty() && stack.peekLast() > 0 && ast < 0) {
                final int top = stack.peekLast();

                if (-ast > top) {
                    stack.removeLast();
                } else if (-ast == top) {
                    stack.removeLast();
                    isColliding = true;
                    break;
                } else {
                    isColliding = true;
                    break;
                }
            }
            if (!isColliding) stack.addLast(ast);
        }
        return stack.stream().mapToInt(Integer::intValue).toArray();
    }
}

class StackTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{5, 10, -5}, new int[]{5, 10}),
            Arguments.of(new int[]{8, -8}, new int[]{}),
            Arguments.of(new int[]{10, 2, -5}, new int[]{10}),
            Arguments.of(new int[]{-2, -1, 1, 2}, new int[]{-2, -1, 1, 2})
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void asteroidCollision(int[] asteroids, int[] expected) {
        assertThat(new Solution().asteroidCollision(asteroids)).isEqualTo(expected);
    }
}
