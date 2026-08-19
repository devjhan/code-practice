from typing import List

import pytest


class Solution:
    def reverseString(self, s: List[str]) -> None:
        """
        Do not return anything, modify s in-place instead.
        """
        s.reverse()


@pytest.mark.parametrize(
    "s, expected",
    [
        (["h", "e", "l", "l", "o"], ["o", "l", "l", "e", "h"]),
        (["H", "a", "n", "n", "a", "h"], ["h", "a", "n", "n", "a", "H"]),
    ],
)
def test_reverseString(s, expected):
    solution = Solution()
    solution.reverseString(s)
    assert s == expected
