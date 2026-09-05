from pathlib import Path

from scripts.scaffold import (
    generate_java_scaffold,
    generate_python_scaffold,
    parse_java_solution,
    parse_python_solution,
)


def test_java_scaffold_imports_tree_node_and_removes_local_definition():
    code = """class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
}
class Solution {
    public int maxDepth(TreeNode root) {
        return 0;
    }
}
"""

    result = generate_java_scaffold(
        Path("problems/leetcode/p0104_maximum_depth/depth_first_search/DepthFirstSearchTest.java"),
        parse_java_solution(code),
    )

    assert "import leetcode.common.TreeNode;" in result
    assert "class TreeNode" not in result
    assert result.count("import leetcode.common.TreeNode;") == 1


def test_java_scaffold_does_not_add_unused_common_import():
    code = """class Solution {
    public int answer(int value) {
        return value;
    }
}
"""

    result = generate_java_scaffold(Path("AnswerTest.java"), parse_java_solution(code))

    assert "import leetcode.common" not in result


def test_python_scaffold_imports_list_node_and_removes_local_definition():
    code = """class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution:
    def reverseList(self, head: ListNode) -> ListNode:
        return head
"""

    result = generate_python_scaffold(
        Path("reverse_linked_list_test.py"), parse_python_solution(code)
    )

    assert "from leetcode.common import ListNode" in result
    assert "class ListNode" not in result
    assert result.count("from leetcode.common import ListNode") == 1


def test_python_scaffold_imports_multiple_common_types_once():
    code = """class Solution:
    def solve(self, head: ListNode, root: TreeNode) -> TreeNode:
        return root
"""

    result = generate_python_scaffold(Path("solve_test.py"), parse_python_solution(code))

    assert "from leetcode.common import ListNode, TreeNode" in result
