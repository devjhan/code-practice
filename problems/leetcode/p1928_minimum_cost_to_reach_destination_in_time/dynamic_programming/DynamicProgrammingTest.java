package leetcode.p1928_minimum_cost_to_reach_destination_in_time.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        List<int[]>[] adjacentList = new List[passingFees.length];
        for (int i = 0; i < passingFees.length; i++) {
            adjacentList[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adjacentList[edge[0]].add(new int[] {edge[1], edge[2]});
            adjacentList[edge[1]].add(new int[] {edge[0], edge[2]});
        }

        int[][] dp = new int[passingFees.length][maxTime + 1];
        for (int i = 0; i < passingFees.length; ++i) Arrays.fill(dp[i], Integer.MAX_VALUE);

        dp[0][0] = passingFees[0];

        for (int t = 0; t <= maxTime; ++t) {
            for (int i = 0; i < passingFees.length; ++i) {
                if (dp[i][t] == Integer.MAX_VALUE) continue;

                for (int[] edge: adjacentList[i]) {
                    final int to = edge[0];
                    final int nextTime = t + edge[1];

                    if (nextTime <= maxTime) dp[to][nextTime] = Math.min(dp[i][t] + passingFees[to], dp[to][nextTime]);
                }
            }
        }
        final int res = Arrays.stream(dp[passingFees.length - 1]).min().getAsInt();
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(29, new int[][]{{0,1,10},{1,2,10},{2,5,10},{0,3,1},{3,4,10},{4,5,15}}, new int[]{5,1,2,20,20,3}, 48),
            Arguments.of(30, new int[][]{{0,1,10},{1,2,10},{2,5,10},{0,3,1},{3,4,10},{4,5,15}}, new int[]{5,1,2,20,20,3}, 11)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void minCost(int maxTime, int[][] edges, int[] passingFees, int expected) {
        assertThat(new Solution().minCost(maxTime, edges, passingFees)).isEqualTo(expected);
    }
}
