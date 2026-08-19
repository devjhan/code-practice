package leetcode.p0078_subsets;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    void backTrack(int[] nums, int firstIndex, List<Integer> status, List<List<Integer>> results) {
        if (firstIndex >= nums.length) {
            results.add(new ArrayList<>(status));
            return;
        }
        status.add(nums[firstIndex]);
        backTrack(nums, firstIndex + 1, status, results);
        status.remove(status.size() - 1);
        backTrack(nums, firstIndex + 1, status, results);
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        backTrack(nums, 0, new ArrayList<>(), results);
        return results;
    }
}

class BackTrackingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{1, 2, 3}, List.of(
                List.of(),
                List.of(1),
                List.of(2),
                List.of(3),
                List.of(1, 2),
                List.of(1, 3),
                List.of(2, 3),
                List.of(1, 2, 3)
            )),
            Arguments.of(new int[]{0}, List.of(
                List.of(0),
                List.of()
            ))
        );
    }

    @MethodSource("cases")
    @ParameterizedTest
    void subsets(int[] nums, List<List<Integer>> expected) {
        assertThat(new Solution().subsets(nums)).containsExactlyInAnyOrderElementsOf(expected);
    }
}
