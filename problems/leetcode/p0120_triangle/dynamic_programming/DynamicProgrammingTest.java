package leetcode.p0120_triangle.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int[] dp = new int[triangle.size()];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        int prevDiagonal = Integer.MAX_VALUE;

        for (int i = 0; i < triangle.size(); ++i) {
            int temp = dp[0];
            dp[0] += triangle.get(i).get(0);
            prevDiagonal = temp;

            for (int j = 1; j < triangle.get(i).size(); ++j) {
                temp = dp[j];
                dp[j] = Math.min(prevDiagonal, dp[j]) + triangle.get(i).get(j);
                prevDiagonal = temp;
            }
        }
        return Arrays.stream(dp).min().getAsInt();
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(
                List.of(
                    List.of(2),
                    List.of(3, 4),
                    List.of(6, 5, 7),
                    List.of(4, 1, 8, 3)
                ),
                11
            ),
            Arguments.of(
                List.of(
                    List.of(-10)
                ),
                -10
            ),
            Arguments.of(
                List.of(
                    List.of(-1),
                    List.of(-2, -3)
                ),
                -4
            )
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void minimumTotal(List<List<Integer>> triangle, int expected) {
        assertThat(new Solution().minimumTotal(triangle)).isEqualTo(expected);
    }
}
