#!/usr/bin/env python3
import sys
import os
import re
import subprocess
from pathlib import Path

class PlatformEnv:
    @staticmethod
    def get_platform() -> str:
        return sys.platform

    @staticmethod
    def is_windows() -> bool:
        return sys.platform == "win32"

    @staticmethod
    def is_macos() -> bool:
        return sys.platform == "darwin"

    @staticmethod
    def is_linux() -> bool:
        return sys.platform.startswith("linux")

    @staticmethod
    def get_repo_root() -> Path:
        return Path(__file__).resolve().parent.parent

    @classmethod
    def get_venv_python(cls) -> str:
        root = cls.get_repo_root()
        # 1. Windows virtual environment
        win_python = root / ".venv" / "Scripts" / "python.exe"
        if win_python.exists():
            return str(win_python)
        # 2. POSIX virtual environment
        posix_python = root / ".venv" / "bin" / "python"
        if posix_python.exists():
            return str(posix_python)
        # 3. Fallback to current runtime python
        return sys.executable

    @classmethod
    def get_gradle_command(cls) -> list[str]:
        root = cls.get_repo_root()
        if cls.is_windows():
            bat = root / "gradlew.bat"
            return [str(bat)]
        else:
            sh = root / "gradlew"
            return [str(sh)]

    @classmethod
    def get_clipboard_text(cls) -> str:
        try:
            if cls.is_macos():
                return subprocess.check_output(["pbpaste"], text=True)
            elif cls.is_windows():
                return subprocess.check_output(
                    ["powershell.exe", "-NoProfile", "-Command", "Get-Clipboard"],
                    text=True
                )
            else:
                try:
                    return subprocess.check_output(["xclip", "-selection", "clipboard", "-o"], text=True)
                except FileNotFoundError:
                    return subprocess.check_output(["xsel", "-b", "-o"], text=True)
        except Exception as e:
            print(f"Error reading clipboard: {e}", file=sys.stderr)
            return ""

    @staticmethod
    def calculate_java_package(file_path: Path) -> str:
        try:
            problems_idx = file_path.parts.index("problems")
            pkg_parts = file_path.parts[problems_idx + 1 : -1]
            if pkg_parts:
                return ".".join(pkg_parts)
        except ValueError:
            pass
        return "leetcode"

    @staticmethod
    def to_pascal_case(s: str) -> str:
        parts = re.split(r"[-_]", s)
        res = "".join(p[:1].upper() + p[1:] for p in parts if p)
        if not res.endswith("Test") and res != "Solution":
            res += "Test"
        return res
