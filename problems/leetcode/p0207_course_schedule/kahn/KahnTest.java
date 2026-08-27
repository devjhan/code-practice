package leetcode.p0207_course_schedule.kahn;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] adjList = new ArrayList[numCourses];
        Deque<Integer> q = new ArrayDeque<>();
        int[] inDegrees = new int[numCourses];

        for (int i = 0; i < numCourses; ++i) adjList[i] = new ArrayList<>();

        for (int[] prerequisite : prerequisites) {
            adjList[prerequisite[1]].add(prerequisite[0]);
            ++inDegrees[prerequisite[0]];
        }

        int[] startNodes = IntStream.range(0, numCourses).filter(i -> inDegrees[i] == 0).toArray();
        Arrays.stream(startNodes).forEach(q::offer);

        while(!q.isEmpty()) {
            int curr = q.remove();

            for (int next: adjList[curr]) {
                if (--inDegrees[next] == 0) q.offer(next);
            }
        }
        return Arrays.stream(inDegrees).allMatch(degree -> degree == 0);
    }
}

class KahnTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(2, new int[][]{{1, 0}}, true),
            Arguments.of(2, new int[][]{{0, 1}}, true),
            Arguments.of(2, new int[][]{{1, 0}, {0, 1}}, false),
            Arguments.of(2, new int[][]{}, true),
            Arguments.of(5, new int[][]{{1, 4}, {2, 4}, {3, 1}, {3, 2}}, true)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void canFinish(int numCourses, int[][] prerequisites, boolean expected) {
        assertThat(new Solution().canFinish(numCourses, prerequisites)).isEqualTo(expected);
    }
}
