package leetcode.p0098_validate_binary_search_tree.binary_search_tree;

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
    private static boolean isValid(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return isValid(node.left, min, node.val) && isValid(node.right, node.val, max);
    }
    public boolean isValidBST(TreeNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
}

class BinarySearchTreeTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new TreeNode(2, new TreeNode(1), new TreeNode(3)), true),
            Arguments.of(new TreeNode(5, new TreeNode(1), new TreeNode(4, new TreeNode(3), new TreeNode(6))), false)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void isValidBST(TreeNode root, boolean expected) {
        assertThat(new Solution().isValidBST(root)).isEqualTo(expected);
    }
}
