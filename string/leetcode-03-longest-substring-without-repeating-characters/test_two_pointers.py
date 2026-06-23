import pytest


class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        charset = set()
        left = 0
        max_len = 0

        for right in range(len(s)):
            while s[right] in charset:
                charset.remove(s[left])
                left += 1
            charset.add(s[right])
            max_len = max(max_len, right - left + 1)

        return max_len


@pytest.mark.parametrize(
    "s, expected",
    [
        ("abcabcbb", 3),
        ("bbbbb", 1),
        ("pwwkew", 3),
    ],
)
def test_lengthOfLongestSubstring(s, expected):
    assert Solution().lengthOfLongestSubstring(s) == expected
