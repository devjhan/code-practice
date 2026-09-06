package leetcode.p0450_delete_node_in_a_bst.binary_search_tree;

import static org.assertj.core.api.Assertions.assertThat;

import leetcode.common.TreeNode;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            TreeNode successor = root.right;
            while (successor.left != null) successor = successor.left;

            root.val = successor.val;
            root.right = deleteNode(root.right, successor.val);
        }
        return root;
    }
}

class BinarySearchTreeTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(
                new TreeNode(5, new TreeNode(3, new TreeNode(2), new TreeNode(4)), new TreeNode(6, null, new TreeNode(7))),
                3,
                new TreeNode(5, new TreeNode(4, new TreeNode(2), null), new TreeNode(6, null, new TreeNode(7)))
            ),
            Arguments.of(
                new TreeNode(5, new TreeNode(2, null, new TreeNode(4)), new TreeNode(6, null, new TreeNode(7))),
                0,
                new TreeNode(5, new TreeNode(2, null, new TreeNode(4)), new TreeNode(6, null, new TreeNode(7)))
            )
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void deleteNode(TreeNode root, int key, TreeNode expected) {
        assertThat(new Solution().deleteNode(root, key)).isEqualTo(expected);
    }
}
