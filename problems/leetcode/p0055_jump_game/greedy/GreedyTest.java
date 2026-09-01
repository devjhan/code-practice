package leetcode.p0055_jump_game.greedy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public boolean canJump(int[] nums) {
        int maxJumpableIndex = 0;

        for (int i = 0; i <= maxJumpableIndex; i++) {
            maxJumpableIndex = Math.max(maxJumpableIndex, i + nums[i]);
            if (maxJumpableIndex >= nums.length - 1) return true;
        }
        return false;
    }
}

class GreedyTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{2, 3, 1, 1, 4}, true),
            Arguments.of(new int[]{3, 2, 1, 0, 4}, false)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void canJump(int[] nums, boolean expected) {
        assertThat(new Solution().canJump(nums)).isEqualTo(expected);
    }
}
