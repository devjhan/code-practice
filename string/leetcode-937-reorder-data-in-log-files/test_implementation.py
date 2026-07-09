from typing import List

import pytest


class Solution:
    def reorderLogFiles(self, logs: List[str]) -> List[str]:
        for i in range(len(logs)):
            if logs[i].startswith(" "):
                logs[i] = "_ " + logs[i].lstrip()

        letter_logs, digit_logs = (
            [
                log
                for log in logs
                if (
                    log.split()[1].isalpha()
                    or (log.startswith("_") and log.split()[2].isalpha())
                )
            ],
            [
                log
                for log in logs
                if not (
                    log.split()[1].isalpha()
                    or (log.startswith("_") and log.split()[2].isalpha())
                )
            ],
        )
        letter_logs.sort(key=lambda x: (x.split()[1:], x.split()[0]))
        ret_logs = letter_logs + digit_logs
        for i in range(len(ret_logs)):
            if ret_logs[i].startswith("_ "):
                ret_logs[i] = ret_logs[i].lstrip("_")
        print(ret_logs)
        return ret_logs


@pytest.mark.parametrize(
    "logs, expected",
    [
        (
            [
                "dig1 8 1 5 1",
                "let1 art can",
                "dig2 3 6",
                "let2 own kit dig",
                "let3 art zero",
            ],
            [
                "let1 art can",
                "let3 art zero",
                "let2 own kit dig",
                "dig1 8 1 5 1",
                "dig2 3 6",
            ],
        ),
        (
            ["a1 9 2 3 1", "g1 act car", "zo4 4 7", "ab1 off key dog", "a8 act zoo"],
            ["g1 act car", "a8 act zoo", "ab1 off key dog", "a1 9 2 3 1", "zo4 4 7"],
        ),
        (
            [
                "dig1 8 1 5 1",
                " let1 art can",
                "dig2 3 6",
                "let2 own kit dig",
                "let3 art zero",
            ],
            [
                "let3 art zero",
                " let1 art can",
                "let2 own kit dig",
                "dig1 8 1 5 1",
                "dig2 3 6",
            ],
        ),
    ],
)
def test_reorderLogFiles(logs, expected):
    assert Solution().reorderLogFiles(logs) == expected
