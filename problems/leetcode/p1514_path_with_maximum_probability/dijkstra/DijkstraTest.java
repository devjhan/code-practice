package leetcode.p1514_path_with_maximum_probability.dijkstra;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private record State(int node, double prob){}
    private record Edge(int target, double prob){}
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        double[] prob = new double[n];
        List<Edge>[] adjList = new ArrayList[n];
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingDouble(State::prob).reversed());

        for (int i = 0; i < n; ++i) adjList[i] = new ArrayList<>();

        for (int i = 0; i < edges.length; ++i) {
            adjList[edges[i][0]].add(new Edge(edges[i][1], succProb[i]));
            adjList[edges[i][1]].add(new Edge(edges[i][0], succProb[i]));
        }

        prob[start_node] = 1.0f;
        pq.add(new State(start_node, 1.0f));

        while (!pq.isEmpty()) {
            State curr = pq.poll();
            if (curr.prob() < prob[curr.node()]) continue;
            if (curr.node() == end_node) return curr.prob();

            for (Edge neighbor : adjList[curr.node()]) {
                double newProb = curr.prob() * neighbor.prob();

                if (prob[neighbor.target()] < newProb) {
                    prob[neighbor.target()] = newProb;
                    pq.offer(new State(neighbor.target(), newProb));
                }
            }
        }
        return prob[end_node];
    }
}

class DijkstraTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(3, new int[][]{{0, 1}, {1, 2}, {0, 2}}, new double[]{0.5, 0.5, 0.2}, 0, 2, 0.25),
            Arguments.of(3, new int[][]{{0, 1}, {1, 2}, {0, 2}}, new double[]{0.5, 0.5, 0.3}, 0, 2, 0.3),
            Arguments.of(3, new int[][]{{0, 1}}, new double[]{0.5}, 0, 2, 0.0),
            Arguments.of(5, new int[][]{{1, 4}, {2, 4}, {0, 4}, {0, 3}, {0, 2}, {2, 3}}, new double[]{0.37, 0.17, 0.93, 0.23, 0.39, 0.04}, 3, 4, 0.2139)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node, double expected) {
        assertThat(new Solution().maxProbability(n, edges, succProb, start_node, end_node)).isEqualTo(expected);
    }
}
