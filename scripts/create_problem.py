#!/usr/bin/env python3
import argparse
import re
import sys
from pathlib import Path

try:
    from .platform_env import PlatformEnv
except ImportError:  # Direct script execution
    from platform_env import PlatformEnv


def to_snake_case(value: str) -> str:
    normalized = re.sub(r"[^A-Za-z0-9]+", "_", value).strip("_").lower()
    if not normalized:
        raise ValueError("name must contain at least one letter or number")
    return normalized


def normalize_number(value: str) -> str:
    if not value.isdigit() or int(value) < 1:
        raise ValueError("problem number must be a positive integer")
    return f"{int(value):04d}"


def normalize_language(value: str) -> str:
    aliases = {"java": "java", "py": "python", "python": "python"}
    try:
        return aliases[value.strip().lower()]
    except KeyError as error:
        raise ValueError("language must be 'java' or 'python'") from error


def problem_file_path(
    root: Path,
    number: str,
    problem_name: str,
    tactics_name: str,
    language: str,
) -> Path:
    problem = f"p{normalize_number(number)}_{to_snake_case(problem_name)}"
    tactic = to_snake_case(tactics_name)
    normalized_language = normalize_language(language)
    problem_dir = root / "problems" / "leetcode" / problem

    if normalized_language == "java":
        return problem_dir / tactic / f"{PlatformEnv.to_pascal_case(tactic)}.java"
    return problem_dir / f"{tactic}_test.py"


def create_problem_file(
    root: Path,
    number: str,
    problem_name: str,
    tactics_name: str,
    language: str,
) -> Path:
    file_path = problem_file_path(
        root, number, problem_name, tactics_name, language
    )
    file_path.parent.mkdir(parents=True, exist_ok=True)
    file_path.touch(exist_ok=False)
    return file_path


def prompt_if_missing(value: str | None, label: str) -> str:
    if value is not None:
        return value
    return input(f"{label}: ").strip()


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Create an empty LeetCode solution file and its parent directories."
    )
    parser.add_argument("number", nargs="?")
    parser.add_argument("problem_name", nargs="?")
    parser.add_argument("tactics_name", nargs="?")
    parser.add_argument("language", nargs="?")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    number = prompt_if_missing(args.number, "Problem number")
    problem_name = prompt_if_missing(args.problem_name, "Problem name")
    tactics_name = prompt_if_missing(args.tactics_name, "Tactics name")
    language = prompt_if_missing(args.language, "Language (java/python)")

    try:
        file_path = create_problem_file(
            PlatformEnv.get_repo_root(),
            number,
            problem_name,
            tactics_name,
            language,
        )
    except (FileExistsError, ValueError) as error:
        print(f"[create_problem] {error}", file=sys.stderr)
        return 1

    print(file_path.relative_to(PlatformEnv.get_repo_root()))
    return 0


if __name__ == "__main__":
    sys.exit(main())
