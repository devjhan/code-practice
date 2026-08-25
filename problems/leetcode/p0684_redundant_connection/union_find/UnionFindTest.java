package leetcode.p0684_redundant_connection.union_find;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static int find(int[] parents, int child) {
        if (parents[child] <= 0) return child;
        return parents[child] = find(parents, parents[child]);
    }

    private static void union(int[] parents, int a, int b) {
        int rootA = find(parents, a);
        int rootB = find(parents, b);

        if (rootA != rootB) {
            if (parents[rootA] < parents[rootB]) {
                parents[rootA] += parents[rootB];
                parents[rootB] = rootA;
            } else {
                parents[rootB] += parents[rootA];
                parents[rootA] = rootB;
            }
        }
    }
    public int[] findRedundantConnection(int[][] edges) {
        int[] parents = new int[edges.length];
        Arrays.fill(parents, -1);

        for (int i = 0; i < edges.length; ++i) {
            if (find(parents, edges[i][0] - 1) == find(parents, edges[i][1] - 1)) return edges[i];
            union(parents, edges[i][0] - 1, edges[i][1] - 1);
        }
        return null;
    }
}

class UnionFindTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][]{{1, 2}, {1, 3}, {2, 3}}, new int[]{2, 3}),
            Arguments.of(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}}, new int[]{1, 4})
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void findRedundantConnection(int[][] edges, int[] expected) {
        assertThat(new Solution().findRedundantConnection(edges)).isEqualTo(expected);
    }
}
