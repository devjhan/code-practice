package leetcode.p0875_koko_eating_bananas.binary_search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static boolean possible(int[] piles, int k, int h) {
        return Arrays.stream(piles).map(val -> (val / k) + (val % k == 0 ? 0 : 1)).mapToLong(val -> (long) val).sum() <= h;
    }

    public int minEatingSpeed(int[] piles, int h) {
        int answer = 0;
        int left = 1, right = Arrays.stream(piles).max().getAsInt();

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (possible(piles, mid, h)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return answer;
    }
}

class BinarySearchTest {
    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new int[] {3, 6, 7, 11}, 8, 4),
                Arguments.of(new int[] {30, 11, 23, 4, 20}, 5, 30),
                Arguments.of(new int[] {30, 11, 23, 4, 20}, 6, 23),
                Arguments.of(new int[] {805306368, 805306368, 805306368}, 1000000000, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void minEatingSpeed(int[] piles, int h, int expected) {
        assertThat(new Solution().minEatingSpeed(piles, h)).isEqualTo(expected);
    }
}
