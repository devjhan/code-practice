#!/usr/bin/env python3
import sys
import subprocess
from pathlib import Path

# Add scripts directory to path to import platform_env
sys.path.insert(0, str(Path(__file__).resolve().parent))
from platform_env import PlatformEnv

def run_python_tests(root: Path, file_path: str = None) -> int:
    python_bin = PlatformEnv.get_venv_python()
    cmd = [python_bin, "-m", "pytest", "-v"]
    if file_path:
        cmd.append(file_path)
    print(f"==> Running Python tests: {' '.join(cmd)}")
    return subprocess.call(cmd, cwd=root)

def run_java_tests(root: Path, test_filter: str = None) -> int:
    cmd = list(PlatformEnv.get_gradle_command())
    cmd.append("test")
    if test_filter:
        cmd.extend(["--tests", test_filter])
    print(f"==> Running Java tests: {' '.join(cmd)}")
    return subprocess.call(cmd, cwd=root)

def main() -> int:
    root = PlatformEnv.get_repo_root()
    
    if len(sys.argv) < 2:
        print("Usage: test_runner.py <filepath | --all | --java | --python>")
        py_status = run_python_tests(root)
        java_status = run_java_tests(root)
        return py_status or java_status

    target = sys.argv[1]

    if target == "--all":
        py_status = run_python_tests(root)
        java_status = run_java_tests(root)
        return py_status or java_status
    elif target == "--java":
        return run_java_tests(root)
    elif target == "--python":
        return run_python_tests(root)

    target_path = Path(target).resolve()
    
    if not target_path.exists():
        target_path = (root / target).resolve()

    if target_path.suffix == ".py":
        return run_python_tests(root, str(target_path))
    elif target_path.suffix == ".java":
        pkg = PlatformEnv.calculate_java_package(target_path)
        cls_name = PlatformEnv.to_pascal_case(target_path.stem)
        fqcn = f"{pkg}.{cls_name}"
        return run_java_tests(root, fqcn)
    else:
        print(f"[test_runner] Not a test file: {target}")
        print("[test_runner] Running all tests instead...")
        py_status = run_python_tests(root)
        java_status = run_java_tests(root)
        return py_status or java_status

if __name__ == "__main__":
    sys.exit(main())
