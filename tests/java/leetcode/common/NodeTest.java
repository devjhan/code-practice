package leetcode.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NodeTest {
    @Test
    void treeNodeUsesLeetCodeCompatibleFields() {
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(3);

        TreeNode root = new TreeNode(2, left, right);

        assertThat(root.val).isEqualTo(2);
        assertThat(root.left).isSameAs(left);
        assertThat(root.right).isSameAs(right);
    }

    @Test
    void listNodeUsesLeetCodeCompatibleFields() {
        ListNode tail = new ListNode(2);

        ListNode head = new ListNode(1, tail);

        assertThat(head.val).isEqualTo(1);
        assertThat(head.next).isSameAs(tail);
    }
}
