from leetcode.common import (
    ListNode,
    TreeNode,
    linked_list_to_list,
    to_linked_list,
)


def test_tree_node_uses_leetcode_compatible_fields():
    left = TreeNode(1)
    right = TreeNode(3)
    root = TreeNode(2, left, right)

    assert (root.val, root.left, root.right) == (2, left, right)


def test_linked_list_round_trip():
    head = to_linked_list([2, 4, 3])

    assert isinstance(head, ListNode)
    assert linked_list_to_list(head) == [2, 4, 3]
    assert to_linked_list([]) is None
