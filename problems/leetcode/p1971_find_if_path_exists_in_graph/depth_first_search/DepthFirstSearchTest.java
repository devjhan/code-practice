package leetcode.p1971_find_if_path_exists_in_graph.depth_first_search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        List<List<Integer>> adjacentList = IntStream.range(0, n).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());

        for (int i = 0; i < edges.length; ++i) {
            adjacentList.get(edges[i][0]).add(edges[i][1]);
            adjacentList.get(edges[i][1]).add(edges[i][0]);
        }

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(source);

        boolean[] visited = new boolean[n];
        visited[stack.peek()] = true;

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (current == destination) {
                return true;
            }

            for (int neighbor: adjacentList.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    stack.push(neighbor);
                }
            }
        }
        return false;
    }
}

class DepthFirstSearchTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(3,  new int[][] {new int []{0, 1}, new int []{1, 2}, new int []{2, 0}}, 0, 2, true),
            Arguments.of(6,  new int[][] {new int []{0, 1}, new int []{0, 2}, new int []{3, 5}, new int []{5, 4}, new int []{4, 3}}, 0, 5, false)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void validPath(int n, int[][] edges, int source, int destination, boolean expected) {
        assertThat(new Solution().validPath(n, edges, source, destination)).isEqualTo(expected);
    }
}
