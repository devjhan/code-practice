package leetcode.p0218_the_skyline_problem.segment_tree;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SegmentTree {
    int n;
    int[] tree;

    SegmentTree(int[] segments, int[][] buildings) {
        this.n = segments.length;
        this.tree = new int[4 * n];

        for (int[] building: buildings) {
            int ql = Arrays.binarySearch(segments, building[0]);
            int qr = Arrays.binarySearch(segments, building[1]);

            update(1, 0, n, ql, qr, building[2]);
        }
    }

    void update(int curr, int left, int right, int ql, int qr, int value) {
        if (qr <= left || ql >= right) return;

        if (ql <= left && right <= qr) {
            tree[curr] = Math.max(tree[curr], value);
            return;
        }

        int mid = left + (right - left) / 2;
        update(curr * 2, left, mid, ql, qr, value);
        update(curr * 2 + 1, mid, right, ql, qr, value);
    }

    int query(int curr, int left, int right, int qpos, int height) {
        if (left + 1 == right) return Math.max(height, tree[curr]);

        int mid = left + (right - left) / 2;
        if (qpos < mid) return query(curr * 2, left, mid, qpos, Math.max(height, tree[curr]));
        else return query(curr * 2 + 1, mid, right, qpos, Math.max(height, tree[curr]));
    }
}
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        int[] segments = new int[2 * buildings.length];
        for (int i = 0; i < buildings.length; ++i) {
            segments[i] = buildings[i][0];
            segments[i + buildings.length] = buildings[i][1];
        }
        segments = Arrays.stream(segments).distinct().toArray();
        Arrays.sort(segments);

        SegmentTree tree = new SegmentTree(segments, buildings);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < segments.length; ++i) {
            int answer = tree.query(1, 0, segments.length, i, 0);
            if (result.isEmpty() || (answer != result.getLast().get(1))) result.add(Arrays.asList(segments[i], answer));
        }
        return result;
    }
}

class SegmentTreeTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][]{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}},
                Arrays.asList(
                    Arrays.asList(2, 10),
                    Arrays.asList(3, 15),
                    Arrays.asList(7, 12),
                    Arrays.asList(12, 0),
                    Arrays.asList(15, 10),
                    Arrays.asList(20, 8),
                    Arrays.asList(24, 0)
                ))
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void getSkyline(int[][] buildings, List<List<Integer>> expected) {
        assertThat(new Solution().getSkyline(buildings)).containsExactlyElementsOf(expected);
    }
}
