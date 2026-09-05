from pathlib import Path

import pytest

import subprocess

from scripts.commit_solution import commit_and_push, commit_scope, problem_directory


class FakeGitRunner:
    def __init__(self, responses: dict[tuple[str, ...], tuple[int, str]]):
        self.responses = responses
        self.calls: list[tuple[str, ...]] = []

    def __call__(
        self, args: list[str], *, check: bool = True
    ) -> subprocess.CompletedProcess[str]:
        key = tuple(args)
        self.calls.append(key)
        returncode, stdout = self.responses.get(key, (0, ""))
        result = subprocess.CompletedProcess(args, returncode, stdout, "")
        if check and returncode:
            raise subprocess.CalledProcessError(returncode, args)
        return result


def test_problem_directory_and_hyphenated_scope(tmp_path: Path):
    current_file = (
        tmp_path
        / "problems"
        / "leetcode"
        / "p0098_validate_binary_search_tree"
        / "binary_search_tree"
        / "BinarySearchTreeTest.java"
    )

    directory = problem_directory(tmp_path, current_file)

    assert directory == (
        tmp_path / "problems/leetcode/p0098_validate_binary_search_tree"
    )
    assert commit_scope(directory) == "0098-validate-binary-search-tree"


def test_commit_scope_preserves_existing_name_case():
    assert (
        commit_scope(Path("p0045_jump_game_II")) == "0045-jump-game-II"
    )


@pytest.mark.parametrize(
    "relative",
    ["README.md", "problems/other/p0001_two_sum/solution.py"],
)
def test_problem_directory_rejects_non_leetcode_paths(
    tmp_path: Path, relative: str
):
    with pytest.raises(ValueError):
        problem_directory(tmp_path, tmp_path / relative)


def test_commit_and_push_stages_only_problem_and_uses_automatic_message(
    tmp_path: Path,
):
    problem = Path("problems/leetcode/p0098_validate_binary_search_tree")
    current_file = tmp_path / problem / "binary_search_tree/Test.java"
    changed_file = f"{problem}/binary_search_tree/Test.java\0"
    runner = FakeGitRunner(
        {
            ("branch", "--show-current"): (0, "main\n"),
            ("diff", "--cached", "--name-only", "-z"): (0, ""),
            (
                "diff",
                "--cached",
                "--name-only",
                "-z",
                "--",
                str(problem),
            ): (0, changed_file),
            (
                "rev-parse",
                "--abbrev-ref",
                "--symbolic-full-name",
                "@{upstream}",
            ): (0, "origin/main\n"),
        }
    )

    message = commit_and_push(tmp_path, current_file, runner)

    assert message == "feat(0098-validate-binary-search-tree): solve problem"
    assert ("add", "-A", "--", str(problem)) in runner.calls
    assert ("commit", "-m", message) in runner.calls
    assert runner.calls[-1] == ("push",)


def test_commit_and_push_rejects_staged_changes_outside_problem(tmp_path: Path):
    current_file = (
        tmp_path
        / "problems/leetcode/p0098_validate_binary_search_tree/solution.py"
    )
    runner = FakeGitRunner(
        {
            ("branch", "--show-current"): (0, "main\n"),
            ("diff", "--cached", "--name-only", "-z"): (
                0,
                "README.md\0",
            ),
        }
    )

    with pytest.raises(RuntimeError, match="outside the current problem"):
        commit_and_push(tmp_path, current_file, runner)

    assert not any(call[0] == "add" for call in runner.calls)
