import pytest


class Solution:
    def longstPalindrome(self, s: str) -> str:
        pal_matrix = [
            [True if row == col else False for col in range(len(s))]
            for row in range(len(s))
        ]

        for i in range(len(s) - 1):
            if s[i] == s[i + 1]:
                pal_matrix[i][i + 1] = True

        for i in range(len(s) - 2, 0, -1):
            for j in range(i, len(s) - 1):
                if pal_matrix[i][j] and s[i - 1] == s[j + 1]:
                    pal_matrix[i - 1][j + 1] = True

        len_max_sub_pal, start_idx, end_idx = 1, 0, 0

        for start, row in enumerate(pal_matrix):
            for end, is_palindrome in enumerate(row):
                if is_palindrome and len_max_sub_pal < end - start + 1:
                    len_max_sub_pal = end - start + 1
                    start_idx, end_idx = start, end

        return s[start_idx : end_idx + 1]


@pytest.mark.parametrize(
    "s, expected",
    [
        ("babad", "bab"),
        ("cbbd", "bb"),
    ],
)
def test_solution(s, expected):
    assert Solution().longstPalindrome(s) == expected
