from pathlib import Path

import pytest

from scripts.create_problem import create_problem_file, problem_file_path


@pytest.mark.parametrize(
    "language, expected",
    [
        (
            "java",
            "problems/leetcode/p0098_validate_binary_search_tree/"
            "binary_search_tree/BinarySearchTreeTest.java",
        ),
        (
            "python",
            "problems/leetcode/p0098_validate_binary_search_tree/"
            "binary_search_tree_test.py",
        ),
    ],
)
def test_problem_file_path_normalizes_inputs(
    tmp_path: Path, language: str, expected: str
):
    result = problem_file_path(
        tmp_path,
        "98",
        "Validate Binary Search Tree",
        "Binary Search Tree",
        language,
    )

    assert result == tmp_path / expected


def test_create_problem_file_creates_empty_file_and_rejects_overwrite(
    tmp_path: Path,
):
    created = create_problem_file(
        tmp_path, "1", "Two Sum", "hash table", "py"
    )

    assert created.is_file()
    assert created.read_text() == ""
    with pytest.raises(FileExistsError):
        create_problem_file(tmp_path, "1", "Two Sum", "hash table", "py")


@pytest.mark.parametrize(
    "number, name, tactic, language",
    [
        ("zero", "Two Sum", "hash_table", "python"),
        ("1", "---", "hash_table", "python"),
        ("1", "Two Sum", "---", "python"),
        ("1", "Two Sum", "hash_table", "kotlin"),
    ],
)
def test_problem_file_path_rejects_invalid_inputs(
    tmp_path: Path, number: str, name: str, tactic: str, language: str
):
    with pytest.raises(ValueError):
        problem_file_path(tmp_path, number, name, tactic, language)
