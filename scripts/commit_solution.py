#!/usr/bin/env python3
import re
import subprocess
import sys
from pathlib import Path
from typing import Protocol

try:
    from .platform_env import PlatformEnv
except ImportError:  # Direct script execution
    from platform_env import PlatformEnv

PROBLEM_DIRECTORY = re.compile(r"^p\d+_[A-Za-z0-9_]+$")


class GitRunner(Protocol):
    def __call__(
        self, args: list[str], *, check: bool = True
    ) -> subprocess.CompletedProcess[str]: ...


def problem_directory(root: Path, file_path: Path) -> Path:
    try:
        relative = file_path.resolve().relative_to(root.resolve())
    except ValueError as error:
        raise ValueError("current file is outside the repository") from error

    parts = relative.parts
    if len(parts) < 4 or parts[:2] != ("problems", "leetcode"):
        raise ValueError("current file is not inside a LeetCode problem")
    if not PROBLEM_DIRECTORY.fullmatch(parts[2]):
        raise ValueError("LeetCode problem directory must look like p0001_two_sum")
    return root / Path(*parts[:3])


def commit_scope(problem_dir: Path) -> str:
    return problem_dir.name.removeprefix("p").replace("_", "-")


def null_separated_paths(output: str) -> list[Path]:
    return [Path(value) for value in output.split("\0") if value]


def is_within(path: Path, directory: Path) -> bool:
    return path == directory or directory in path.parents


def make_git_runner(root: Path) -> GitRunner:
    def run(
        args: list[str], *, check: bool = True
    ) -> subprocess.CompletedProcess[str]:
        return subprocess.run(
            ["git", *args],
            cwd=root,
            check=check,
            text=True,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
        )

    return run


def commit_and_push(root: Path, file_path: Path, run_git: GitRunner) -> str:
    target_dir = problem_directory(root, file_path)
    target_relative = target_dir.relative_to(root)

    branch = run_git(["branch", "--show-current"]).stdout.strip()
    if not branch:
        raise RuntimeError("cannot commit from a detached HEAD")

    staged = null_separated_paths(
        run_git(["diff", "--cached", "--name-only", "-z"]).stdout
    )
    outside_staged = [
        path for path in staged if not is_within(path, target_relative)
    ]
    if outside_staged:
        paths = ", ".join(str(path) for path in outside_staged)
        raise RuntimeError(f"staged changes outside the current problem: {paths}")

    run_git(["add", "-A", "--", str(target_relative)])
    staged_for_problem = null_separated_paths(
        run_git(
            ["diff", "--cached", "--name-only", "-z", "--", str(target_relative)]
        ).stdout
    )
    if not staged_for_problem:
        raise RuntimeError("no changes to commit for the current problem")

    message = f"feat({commit_scope(target_dir)}): solve problem"
    run_git(["commit", "-m", message])

    upstream = run_git(
        ["rev-parse", "--abbrev-ref", "--symbolic-full-name", "@{upstream}"],
        check=False,
    )
    if upstream.returncode == 0:
        run_git(["push"])
    else:
        run_git(["remote", "get-url", "origin"])
        run_git(["push", "--set-upstream", "origin", branch])
    return message


def main() -> int:
    if len(sys.argv) != 2:
        print("Usage: commit_solution.py <current_file>", file=sys.stderr)
        return 1

    root = PlatformEnv.get_repo_root()
    try:
        message = commit_and_push(root, Path(sys.argv[1]), make_git_runner(root))
    except (RuntimeError, ValueError, subprocess.CalledProcessError) as error:
        if isinstance(error, subprocess.CalledProcessError) and error.stderr:
            print(error.stderr.strip(), file=sys.stderr)
        print(f"[commit_solution] {error}", file=sys.stderr)
        return 1

    print(f"Committed and pushed: {message}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
