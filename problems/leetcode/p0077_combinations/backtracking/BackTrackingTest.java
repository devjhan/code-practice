package leetcode.p0077_combinations.backtracking;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private void backTrack(int n, int k, int startIndex, List<Integer> current, List<List<Integer>> result) {
        if (current.size() >= k) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = startIndex; i <= n; ++i) {
            current.add(i);
            backTrack(n, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backTrack(n, k, 1, new ArrayList<>(), result);
        return result;
    }
}

class BackTrackingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(4, 2, new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 2)),
                new ArrayList<>(Arrays.asList(1, 3)),
                new ArrayList<>(Arrays.asList(1, 4)),
                new ArrayList<>(Arrays.asList(2, 3)),
                new ArrayList<>(Arrays.asList(2, 4)),
                new ArrayList<>(Arrays.asList(3, 4))
            ))),
            Arguments.of(1, 1, new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1))
            )))
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void combine(int n, int k, List<List<Integer>> expected) {
        assertThat(new Solution().combine(n, k)).containsExactlyInAnyOrderElementsOf(expected);
    }
}
