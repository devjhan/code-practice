package leetcode.p0300_longest_increasing_subsequence.binary_search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static int lowerBound(int[] tails, int target) {
        int left = 0, right = tails.length - 1;

        while(left <= right) {
            int mid = left + (right - left) / 2;

            if (tails[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
    public int lengthOfLIS(int[] nums) {
        int[] tails = IntStream.generate(() -> Integer.MAX_VALUE).limit(nums.length).toArray();
        int currIndex = 0;

        for (int num: nums) {
            int leastBiggerIndex = lowerBound(tails, num);
            tails[leastBiggerIndex] = num;
            currIndex = Math.max(currIndex, leastBiggerIndex);
        }
        return currIndex + 1;
    }
}

class BinarySearchTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[] {1}, 1),
            Arguments.of(new int[] {10, 9, 2, 5, 3, 7, 101, 18}, 4),
            Arguments.of(new int[] {0, 1, 0, 3, 2, 3}, 4),
            Arguments.of(new int[] {7, 7, 7, 7, 7, 7, 7}, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void lengthOfLIS(int[] nums, int expected) {
        assertThat(new Solution().lengthOfLIS(nums)).isEqualTo(expected);
    }
}
