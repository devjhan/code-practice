package leetcode.p0417_pacific_atlantic_water_flow.breadth_first_search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private int getIndex(int i, int j, final int[][] heights) {
        return i * heights[0].length + j;
    }

    private BitSet getReachables(final int[][] heights, boolean isPacific) {
        BitSet bitSet = new BitSet(heights.length * heights[0].length);
        Queue<int[]> q = new ArrayDeque<>();

        if (isPacific) {
            for (int i = 0; i < heights.length; ++i) {
                bitSet.set(getIndex(i, 0, heights));
                q.offer(new int[]{i, 0});
            }
            for (int j = 0; j < heights[0].length; ++j) {
                bitSet.set(getIndex(0, j, heights));
                q.offer(new int[]{0, j});
            }
        } else {
            for (int i = 0; i < heights.length; ++i) {
                bitSet.set(getIndex(i, heights[0].length - 1, heights));
                q.offer(new int[]{i, heights[0].length - 1});
            }
            for (int j = 0; j < heights[0].length; ++j) {
                bitSet.set(getIndex(heights.length - 1, j, heights));
                q.offer(new int[]{heights.length - 1, j});
            }
        }

        while (!q.isEmpty()) {
            int[] curr = q.poll();

            for (int[] dir : DIRECTIONS) {
                int[] next = {curr[0] + dir[0], curr[1] + dir[1]};

                if (next[0] >= 0 && next[0] < heights.length && next[1] >= 0 && next[1] < heights[0].length) {
                    if (heights[next[0]][next[1]] >= heights[curr[0]][curr[1]]) {
                        if (!bitSet.get(getIndex(next[0], next[1], heights))) {
                            bitSet.set(getIndex(next[0], next[1], heights));
                            q.offer(next);
                        }
                    }
                }
            }
        }
        return bitSet;
    }

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        BitSet intersectedReachables = getReachables(heights, true);
        BitSet atlanticReachables = getReachables(heights, false);

        intersectedReachables.and(atlanticReachables);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < heights.length; ++i) {
            for (int j = 0; j < heights[0].length; ++j) {
                if (intersectedReachables.get(getIndex(i, j, heights))) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        return result;
    }
}

class BreadthFirstSearchTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][]{{1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}},
                Arrays.asList(Arrays.asList(0, 4), Arrays.asList(1, 3), Arrays.asList(1, 4), Arrays.asList(2, 2), Arrays.asList(3, 0), Arrays.asList(3, 1), Arrays.asList(4, 0))),
            Arguments.of(new int[][]{{1}}, Arrays.asList(Arrays.asList(0, 0)))
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void pacificAtlantic(int[][] heights, List<List<Integer>> expected) {
        assertThat(new Solution().pacificAtlantic(heights)).containsExactlyInAnyOrderElementsOf(expected);
    }
}
