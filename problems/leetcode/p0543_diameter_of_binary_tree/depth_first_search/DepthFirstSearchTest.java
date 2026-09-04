package leetcode.p0543_diameter_of_binary_tree.depth_first_search;

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
    private static int dfs(TreeNode node, int[] maxDiameter) {
        if (node == null) return 0;

        int leftHeight = dfs(node.left, maxDiameter);
        int rightHeight = dfs(node.right, maxDiameter);

        maxDiameter[0] = Math.max(maxDiameter[0], leftHeight + rightHeight);
        return Math.max(leftHeight, rightHeight) + 1;
    }
    public int diameterOfBinaryTree(TreeNode root) {
        int[] maxDiameter = new int[1];
        dfs(root, maxDiameter);
        return maxDiameter[0];
    }
}

class DepthFirstSearchTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new TreeNode(1, new TreeNode(2, new TreeNode(4), new TreeNode(5)), new TreeNode(3)), 3),
            Arguments.of(new TreeNode(1, new TreeNode(2), new TreeNode(3)), 2)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void diameterOfBinaryTree(TreeNode root, int expected) {
        assertThat(new Solution().diameterOfBinaryTree(root)).isEqualTo(expected);
    }
}
