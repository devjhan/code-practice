package leetcode.p0198_house_robber.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int rob(int[] nums) {
        if (nums.length == 1) return nums[0];
        int[] prevs = new int[] {nums[0], Math.max(nums[0], nums[1])};

        for (int i = 2; i < nums.length; ++i) {
            int tmp = prevs[1];
            prevs[1] = Math.max(prevs[0] + nums[i], tmp);
            prevs[0] = tmp;
        }
        return prevs[1];
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{1, 2, 3, 1}, 4),
            Arguments.of(new int[]{2, 7, 9, 3, 1}, 12),
            Arguments.of(new int[]{1, 3, 1}, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void rob(int[] nums, int expected) {
        assertThat(new Solution().rob(nums)).isEqualTo(expected);
    }
}
