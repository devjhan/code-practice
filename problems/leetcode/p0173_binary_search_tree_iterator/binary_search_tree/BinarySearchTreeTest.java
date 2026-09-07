import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

import leetcode.common.TreeNode;

class BSTIterator {
    private Deque<TreeNode> stack;
    public BSTIterator(TreeNode root) {
        this.stack = new ArrayDeque<>();
        TreeNode temp = root;

        while (temp.left != null) {
            this.stack.push(temp);
            temp = temp.left;
        }
        this.stack.push(temp);
    }

    private void pushLeft(TreeNode node) {
        if (node == null) return;
        TreeNode temp = node.right;
        if (temp == null) return;

        while (temp.left != null) {
            this.stack.push(temp);
            temp = temp.left;
        }
        this.stack.push(temp);
    }

    public int next() {
        TreeNode node = this.stack.pop();
        pushLeft(node);
        return node.val;
    }

    public boolean hasNext() {
        return !this.stack.isEmpty();
    }
}

public class BinarySearchTreeTest {

}
