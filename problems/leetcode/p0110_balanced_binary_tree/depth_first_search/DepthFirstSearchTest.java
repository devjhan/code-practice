package leetcode.p0110_balanced_binary_tree.depth_first_search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode() {}
     TreeNode(int val) { this.val = val; }
     TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
         this.left = left;
         this.right = right;
     }
 }
class Solution {
    private static int dfs(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = dfs(node.left);
        int rightHeight = dfs(node.right);

        return (leftHeight  == -1 || rightHeight == -1) || (Math.abs(leftHeight - rightHeight) > 1) ? -1 : Math.max(leftHeight, rightHeight) + 1;
    }
    public boolean isBalanced(TreeNode root) {
        return dfs(root) != -1;
    }
}

class DepthFirstSearchTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7))), true),
            Arguments.of(new TreeNode(1, new TreeNode(2, new TreeNode(3, new TreeNode(4), new TreeNode(4)), new TreeNode(3)), new TreeNode(2)), false)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void isBalanced(TreeNode root, boolean expected) {
        assertThat(new Solution().isBalanced(root)).isEqualTo(expected);
    }
}
