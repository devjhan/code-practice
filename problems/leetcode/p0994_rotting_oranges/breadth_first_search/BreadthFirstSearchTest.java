package leetcode.p0994_rotting_oranges.breadth_first_search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static record Coordinate(int i, int j){}
    final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int orangesRotting(int[][] grid) {
        int freshOrangesCount = 0;
        int timeSlices = 0;
        Deque<Coordinate> rottenOrangesQueue = new ArrayDeque<>();

        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] == 1) {
                    ++freshOrangesCount;
                } else if (grid[i][j] == 2) {
                    rottenOrangesQueue.offerLast(new Coordinate(i, j));
                }
            }
        }
        int prevFreshOrangesCount = freshOrangesCount;

        while (!rottenOrangesQueue.isEmpty()) {
            int size = rottenOrangesQueue.size();

            for (int i = 0; i < size; ++i) {
                Coordinate pos = rottenOrangesQueue.poll();

                for (int[] delta: deltas) {
                    Coordinate nextCoordinate = new Coordinate(pos.i() + delta[0], pos.j() + delta[1]);

                    if (nextCoordinate.i() < 0 || nextCoordinate.i() >= grid.length || nextCoordinate.j() < 0 || nextCoordinate.j() >= grid[nextCoordinate.i()].length) {
                        continue;
                    }

                    if (grid[nextCoordinate.i()][nextCoordinate.j()] == 1) {
                        rottenOrangesQueue.offerLast(nextCoordinate);
                        grid[nextCoordinate.i()][nextCoordinate.j()] = 2;
                        --freshOrangesCount;
                    }
                }
            }
            if (prevFreshOrangesCount != freshOrangesCount) {
                prevFreshOrangesCount = freshOrangesCount;
                ++timeSlices;
            }
        }
        return (freshOrangesCount == 0) ? timeSlices : -1;
    }
}

class BreadthFirstSearchTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][] {{2, 1, 1}, {1, 1, 0}, {0, 1, 1}}, 4),
            Arguments.of(new int[][] {{2, 1, 1}, {0, 1, 1}, {1, 0, 1}}, -1),
            Arguments.of(new int[][] {{0, 2}}, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void orangesRotting(int[][] grid, int expected) {
        assertThat(new Solution().orangesRotting(grid)).isEqualTo(expected);
    }
}
