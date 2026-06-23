from typing import Dict, List

import pytest


class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        hashtable = {}

        for idx, num in enumerate(nums):
            comp = target - num

            if comp in hashtable:
                return [hashtable[comp], idx]
            else:
                hashtable[num] = idx
        return []


@pytest.mark.parametrize(
    "nums, target, expected",
    [
        ([2, 7, 11, 15], 9, [0, 1]),
        ([3, 2, 4], 6, [1, 2]),
        ([3, 3], 6, [0, 1]),
    ],
)
def test_solution(nums, target, expected):
    assert Solution().twoSum(nums, target) == expected
