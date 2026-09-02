package leetcode.p0045_jump_game_II.greedy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int jump(int[] nums) {
        int maxJumpableIndex = 0;
        int jumpCount = 0;
        int currJumpRange = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            maxJumpableIndex = Math.max(maxJumpableIndex, i + nums[i]);

            if (currJumpRange == i) {
                ++jumpCount;
                currJumpRange = maxJumpableIndex;
            }
        }
        return jumpCount;
    }
}

class GreedyTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{2,3,1,1,4}, 2),
            Arguments.of(new int[]{1,1,1,1,1}, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void jump(int[] nums, int expected) {
        assertThat(new Solution().jump(nums)).isEqualTo(expected);
    }
}
