import pytest


class Solution:
    def isPalindrome(self, s: str) -> bool:
        temp = "".join(ch.lower() for ch in s if ch.isalnum())

        start, end = 0, len(temp) - 1

        while start < end:
            if temp[start] != temp[end]:
                return False
            start += 1
            end -= 1
        return True


@pytest.mark.parametrize(
    "s, expected",
    [
        ("A man, a plan, a canal: Panama", True),
        ("race a car", False),
        (" ", True),
    ],
)
def test_isPalindrome(s, expected):
    assert Solution().isPalindrome(s) == expected
