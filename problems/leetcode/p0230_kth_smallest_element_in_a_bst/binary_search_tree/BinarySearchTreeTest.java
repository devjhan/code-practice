package leetcode.p0230_kth_smallest_element_in_a_bst.binary_search_tree;

import static org.assertj.core.api.Assertions.assertThat;

import leetcode.common.TreeNode;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static void dfs(TreeNode node, int[] count, int k, int[] answer, boolean[] answerFound) {
        if (node == null) return;
        dfs(node.left, count, k, answer, answerFound);
        if (answerFound[0]) return;

        if (++count[0] == k) {
            answer[0] = node.val;
            answerFound[0] = true;
            return;
        }

        dfs(node.right, count, k, answer, answerFound);
        return;
    }
    public int kthSmallest(TreeNode root, int k) {
        int[] answer = new int[1];
        boolean[] answerFound = new boolean[1];
        dfs(root, new int[]{0}, k, answer, answerFound);
        return answer[0];
    }
}

class BinarySearchTreeTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(
                new TreeNode(3, new TreeNode(1, null, new TreeNode(2)), new TreeNode(4)),
                1,
                1
            ),
            Arguments.of(
                new TreeNode(5, new TreeNode(3, new TreeNode(2, new TreeNode(1), null), new TreeNode(4)), new TreeNode(6)),
                3,
                3
            )
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void kthSmallest(TreeNode root, int k, int expected) {
        assertThat(new Solution().kthSmallest(root, k)).isEqualTo(expected);
    }
}
