package leetcode.p0525_contiguous_array;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int findMaxLength(int[] nums) {
        int counterOneMinusZero = 0;
        int maxLength = 0;
        HashMap<Integer, Integer> firstIndex = new HashMap<>();

        firstIndex.put(0, -1);

        for (int i = 0; i < nums.length; ++i) {
            counterOneMinusZero += ((nums[i] == 1) ? 1 : -1);

            if (firstIndex.containsKey(counterOneMinusZero)) {
                maxLength = Math.max(maxLength, i - firstIndex.get(counterOneMinusZero));
            } else {
                firstIndex.put(counterOneMinusZero, i);
            }
        }
        return maxLength;
    }
}

class HashMapTest {
    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new int[] {0, 1}, 2),
                Arguments.of(new int[] {0, 1, 0}, 2),
                Arguments.of(new int[] {0, 1, 1, 1, 1, 1, 0, 0, 0}, 6)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void findMaxLength(int[] nums, int expected) {
        assertThat(new Solution().findMaxLength(nums)).isEqualTo(expected);
    }
}
