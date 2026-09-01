package leetcode.p0435_non_overlapping_intervals.greedy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[1]));
        int removalCount = 0, currentEndTime = Integer.MIN_VALUE;

        for (int[] interval: intervals) {
            if (currentEndTime <= interval[0]) currentEndTime = interval[1];
            else ++removalCount;
        }
        return removalCount;
    }
}

class GreedyTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}}, 1),
            Arguments.of(new int[][]{{1, 2}, {1, 2}, {1, 2}}, 2),
            Arguments.of(new int[][]{{1, 2}, {2, 3}}, 0),
            Arguments.of(new int[][]{{-50000, 1}}, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void eraseOverlapIntervals(int[][] intervals, int expected) {
        assertThat(new Solution().eraseOverlapIntervals(intervals)).isEqualTo(expected);
    }
}
