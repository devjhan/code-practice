#!/usr/bin/env python3
import os
import sys
import subprocess
from pathlib import Path

def get_repo_root() -> Path:
    current = Path(__file__).resolve().parent.parent
    return current

def find_python_executable(root: Path) -> str:
    venv_python = root / '.venv' / 'bin' / 'python'
    if venv_python.exists():
        return str(venv_python)
    return sys.executable

def run_python_tests(root: Path, file_path: str = None) -> int:
    python_bin = find_python_executable(root)
    cmd = [python_bin, '-m', 'pytest', '-v']
    if file_path:
        cmd.append(file_path)
    print(f'==> Running Python tests: {" ".join(cmd)}')
    return subprocess.call(cmd, cwd=root)

def run_java_tests(root: Path, test_filter: str = None) -> int:
    gradlew = root / 'gradlew'
    cmd = [str(gradlew), 'test']
    if test_filter:
        cmd.extend(['--tests', f'*{test_filter}*'])
    print(f'==> Running Java tests: {" ".join(cmd)}')
    return subprocess.call(cmd, cwd=root)

def main() -> int:
    root = get_repo_root()
    
    if len(sys.argv) < 2:
        print('Usage: test_runner.py <filepath | --all | --java | --python>')
        # default to all tests
        py_status = run_python_tests(root)
        java_status = run_java_tests(root)
        return py_status or java_status

    target = sys.argv[1]

    if target == '--all':
        py_status = run_python_tests(root)
        java_status = run_java_tests(root)
        return py_status or java_status
    elif target == '--java':
        return run_java_tests(root)
    elif target == '--python':
        return run_python_tests(root)

    target_path = Path(target).resolve()
    
    if not target_path.exists():
        # Try relative to root
        target_path = (root / target).resolve()

    if target_path.suffix == '.py':
        return run_python_tests(root, str(target_path))
    elif target_path.suffix == '.java':
        class_name = target_path.stem
        return run_java_tests(root, class_name)
    else:
        print(f'[test_runner] Not a test file: {target}')
        print('[test_runner] Running all tests instead...')
        py_status = run_python_tests(root)
        java_status = run_java_tests(root)
        return py_status or java_status

if __name__ == '__main__':
    sys.exit(main())
