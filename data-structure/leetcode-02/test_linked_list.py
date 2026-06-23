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
            ge_ten, curr = divmod(
                (l1.val if l1 is not None else 0)
                + (l2.val if l2 is not None else 0)
                + output.val,
                10,
            )
            l1, l2 = (
                l1.next if l1 is not None else l1,
                l2.next if l2 is not None else l2,
            )

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
