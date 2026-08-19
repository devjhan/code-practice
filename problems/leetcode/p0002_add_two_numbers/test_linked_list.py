from typing import List, Optional

import pytest

# Definition for singly-linked list.


class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


def to_linked_list(values: List[int]) -> Optional[ListNode]:
    dummy = ListNode()
    curr = dummy
    for value in values:
        curr.next = ListNode(value)
        curr = curr.next
    return dummy.next


def linked_list_to_list(head: Optional[ListNode]) -> List[int]:
    values = []
    while head:
        values.append(head.val)
        head = head.next
    return values


class Solution:
    def addTwoNumbers(
        self, l1: Optional[ListNode], l2: Optional[ListNode]
    ) -> Optional[ListNode]:
        output = ListNode()
        output_ptr = output

        while l1 or l2:
            ge_ten, curr = divmod(
                (l1.val if l1 else 0) + (l2.val if l2 else 0) + output.val,
                10,
            )
            l1, l2 = (
                l1.next if l1 else l1,
                l2.next if l2 else l2,
            )

            output.val = curr
            if (l1 or l2) or ge_ten:
                output.next = ListNode(val=ge_ten)
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
    result = Solution().addTwoNumbers(to_linked_list(l1), to_linked_list(l2))
    assert linked_list_to_list(result) == expected
