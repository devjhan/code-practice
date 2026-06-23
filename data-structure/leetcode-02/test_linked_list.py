from typing import Optional

import pytest

# Definition for singly-linked list.


class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class Solution:
    def addTwoNumbers(
        self, l1: Optional[ListNode], l2: Optional[ListNode]
    ) -> Optional[ListNode]:
        output = ListNode()
        output_ptr = output

        while l1 is not None or l2 is not None:
            curr, ge_ten = 0, False

            if l1 is None:
                curr, ge_ten = (
                    (l2.val + output.val) % 10,
                    ((l2.val + output.val) // 10 == 1),
                )
                l2 = l2.next
            elif l2 is None:
                curr, ge_ten = (
                    (l1.val + output.val) % 10,
                    ((l1.val + output.val) // 10 == 1),
                )
                l1 = l1.next
            else:
                curr, ge_ten = (
                    (l1.val + l2.val + output.val) % 10,
                    ((l1.val + l2.val + output.val) // 10 == 1),
                )
                l1, l2 = l1.next, l2.next

            output.val = curr
            if (l1 is not None or l2 is not None) or ge_ten:
                output.next = ListNode(val=int(ge_ten))
                output = output.next

        return output_ptr


@pytest.mark.parametrize(
    "l1, l2, expected",
    [
        ([2, 4, 3], [5, 6, 4], [7, 0, 8]),
        ([0], [0], [0]),
        ([9, 9, 9, 9, 9, 9, 9], [9, 9, 9, 9], [8, 9, 9, 9, 0, 0, 0, 1]),
    ],
)
def test_addTwoNumbers(l1, l2, expected):
    assert Solution().addTwoNumbers(l1, l2) == expected
