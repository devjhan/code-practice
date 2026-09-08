package leetcode.p0114_flatten_binary_tree_to_linked_list.binary_tree;

import static org.assertj.core.api.Assertions.assertThat;

import leetcode.common.TreeNode;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public void flatten(TreeNode root) {
        TreeNode ptr = root;

        while (ptr != null) {
            if (ptr.left != null) {
                TreeNode leftGreatest = ptr.left;
                while (leftGreatest.right != null) leftGreatest = leftGreatest.right;

                TreeNode temp = ptr.right;

                ptr.right = ptr.left;
                leftGreatest.right = temp;
                ptr.left = null;
            }
            ptr = ptr.right;
        }
    }
}

class BinaryTreeTest {
}
