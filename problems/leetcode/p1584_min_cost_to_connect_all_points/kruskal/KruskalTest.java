package leetcode.p1584_min_cost_to_connect_all_points.kruskal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static int find(int[] parents, int child) {
        if (parents[child] < 0) return child;
        return parents[child] = find(parents, parents[child]);
    }

    private static void union(int[] parents, int p1, int p2) {
        int rootP1 = find(parents, p1);
        int rootP2 = find(parents, p2);

        if (rootP1 != rootP2) {
            if (parents[rootP1] < parents[rootP2]) {
                parents[rootP1] += parents[rootP2];
                parents[rootP2] = rootP1;
            } else {
                parents[rootP2] += parents[rootP1];
                parents[rootP1] = rootP2;
            }
        }
    }
    private static int calcDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    public int minCostConnectPoints(int[][] points) {
        int[][] edges = new int[points.length * (points.length - 1) / 2][6];
        int edgesPtr = 0;

        int[] parents = new int[points.length];
        Arrays.fill(parents, -1);

        int cost = 0;

        for (int i = 0; i < points.length; ++i) {
            for (int j = i + 1; j < points.length; ++j) {
                edges[edgesPtr++] = new int[] {points[i][0], points[i][1], i, points[j][0], points[j][1], j};
            }
        }
        Arrays.sort(edges, Comparator.comparingInt(edge -> calcDist(edge[0], edge[1], edge[3], edge[4])));

        for (int[] edge: edges) {
            if (find(parents, edge[2]) != find(parents, edge[5])) {
                union(parents, edge[2], edge[5]);
                cost += calcDist(edge[0], edge[1], edge[3], edge[4]);
            }
        }
        return cost;
    }
}

class KruskalTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][] {{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}}, 20),
            Arguments.of(new int[][] {{3, 12}, {-2, 5}, {-4, 1}}, 18)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void minCostConnectPoints(int[][] points, int expected) {
        assertThat(new Solution().minCostConnectPoints(points)).isEqualTo(expected);
    }
}
