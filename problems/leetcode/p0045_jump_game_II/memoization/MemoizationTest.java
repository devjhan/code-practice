package leetcode.p0045_jump_game_II.memoization;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static final int INF = Integer.MAX_VALUE - 1;
    private static int dfs(final int[] nums, int at, int[] memo) {
        if (at >= nums.length) return INF;
        if (at == nums.length - 1) return 0;
        if (memo[at] != INF) return memo[at];

        int answer = INF;

        for (int i = at + 1; i <= at + nums[at]; i++) {
            answer = Math.min(dfs(nums, i, memo) + 1, answer);
        }
        memo[at] = answer;
        return answer;
    }
    public int jump(int[] nums) {
        int[] memo = new int[nums.length];
        Arrays.fill(memo, INF);
        return dfs(nums, 0, memo);
    }
}

class MemoizationTest {
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
