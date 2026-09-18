package leetcode.p0128_longest_consecutive_sequence.hash_set;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int longestConsecutive(int[] nums) {
        HashMap<Integer, Integer> set = new HashMap<>();
        for (int num : nums) set.put(num, null);

        int longest = 0;

        for (int num: set.keySet()) {
            if (!set.containsKey(num - 1)) {
                int ptr = num;

                while (set.containsKey(ptr)) ptr++;

                longest = Math.max(longest, ptr - num);
            }
        }
        return longest;
    }
}

class HashSetTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{100, 4, 200, 1, 3, 2}, 4),
            Arguments.of(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, 9),
            Arguments.of(new int[]{1, 0, 1, 2}, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void longestConsecutive(int[] nums, int expected) {
        assertThat(new Solution().longestConsecutive(nums)).isEqualTo(expected);
    }
}
