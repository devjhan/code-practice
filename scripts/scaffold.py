#!/usr/bin/env python3
import sys
import os
import re
from pathlib import Path

# Add scripts directory to path to import platform_env
sys.path.insert(0, str(Path(__file__).resolve().parent))
from platform_env import PlatformEnv

def parse_java_solution(code: str):
    method_pattern = re.compile(
        r"public\s+([\w<>\[\],\s]+?)\s+([a-zA-Z0-9_]+)\s*\(([\s\S]*?)\)\s*\{",
        re.MULTILINE
    )
    match = method_pattern.search(code)
    if not match:
        return None
    
    return_type = match.group(1).strip()
    method_name = match.group(2).strip()
    raw_params = match.group(3).strip()
    
    params = []
    param_names = []
    if raw_params:
        for p in re.split(r",(?![^<]*>)", raw_params):
            p = p.strip()
            if p:
                params.append(p)
                p_parts = p.rsplit(None, 1)
                if len(p_parts) == 2:
                    param_names.append(p_parts[1])
                else:
                    param_names.append(p)
                    
    return {
        "return_type": return_type,
        "method_name": method_name,
        "params": params,
        "param_names": param_names,
        "raw_params": ", ".join(params),
        "solution_body": code.strip()
    }

def generate_java_scaffold(file_path: Path, parsed: dict) -> str:
    package_name = PlatformEnv.calculate_java_package(file_path)
    class_name = PlatformEnv.to_pascal_case(file_path.stem)
    
    return_type = parsed["return_type"]
    method_name = parsed["method_name"]
    params = parsed["params"]
    param_names = parsed["param_names"]
    solution_body = parsed["solution_body"]
    
    if "class Solution" not in solution_body:
        default_ret = "0"
        if return_type == "void":
            default_ret = ""
        elif return_type == "boolean":
            default_ret = "false"
        elif "[]" in return_type:
            default_ret = "new " + return_type[:-2] + "[0]"
        elif return_type.startswith("List") or return_type.startswith("ArrayList"):
            default_ret = "new ArrayList<>()"
        elif return_type.startswith("String"):
            default_ret = '""'
        elif return_type in ["int", "long", "short", "byte", "char", "double", "float"]:
            default_ret = "0"
        else:
            default_ret = "null"

        ret_stmt = f"\n        return {default_ret};" if default_ret else ""
        solution_body = f"""class Solution {{
    public {return_type} {method_name}({parsed['raw_params']}) {{{ret_stmt}
    }}
}}"""

    if return_type.startswith("List<List<") or return_type.startswith("Set<List<"):
        assert_stmt = f"assertThat(new Solution().{method_name}({', '.join(param_names)})).containsExactlyInAnyOrderElementsOf(expected);"
    elif return_type.startswith("List<") or return_type.startswith("Set<"):
        assert_stmt = f"assertThat(new Solution().{method_name}({', '.join(param_names)})).containsExactlyInAnyOrderElementsOf(expected);"
    elif return_type == "int[]" or return_type == "String[]" or "[]" in return_type:
        assert_stmt = f"assertThat(new Solution().{method_name}({', '.join(param_names)})).isEqualTo(expected);"
    else:
        assert_stmt = f"assertThat(new Solution().{method_name}({', '.join(param_names)})).isEqualTo(expected);"

    test_params_list = list(params)
    test_params_list.append(f"{return_type} expected")
    test_params_str = ", ".join(test_params_list)

    return f"""package {package_name};

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

{solution_body}

class {class_name} {{
    static Stream<Arguments> cases() {{
        return Stream.of(
            Arguments.of(/* args, expected */)
        );
    }}

    @ParameterizedTest
    @MethodSource("cases")
    void {method_name}({test_params_str}) {{
        {assert_stmt}
    }}
}}
"""

def parse_python_solution(code: str):
    method_pattern = re.compile(
        r"def\s+([a-zA-Z0-9_]+)\s*\(\s*self\s*,?\s*([\s\S]*?)\)\s*(?:->\s*([\w\[\],\s]+?))?\s*:",
        re.MULTILINE
    )
    match = method_pattern.search(code)
    if not match:
        return None
    
    method_name = match.group(1).strip()
    raw_params = match.group(2).strip()
    return_type = match.group(3).strip() if match.group(3) else ""
    
    params = []
    param_names = []
    if raw_params:
        for p in re.split(r",(?![^<]*>)", raw_params):
            p = p.strip()
            if p:
                params.append(p)
                p_name = p.split(":")[0].strip()
                param_names.append(p_name)

    return {
        "method_name": method_name,
        "params": params,
        "param_names": param_names,
        "return_type": return_type,
        "solution_body": code.strip()
    }

def generate_python_scaffold(file_path: Path, parsed: dict) -> str:
    method_name = parsed["method_name"]
    params = parsed["params"]
    param_names = parsed["param_names"]
    solution_body = parsed["solution_body"]
    
    if "class Solution" not in solution_body:
        ret_type_annot = f" -> {parsed['return_type']}" if parsed['return_type'] else ""
        raw_params = ", ".join(params)
        solution_body = f"""class Solution:
    def {method_name}(self, {raw_params}){ret_type_annot}:
        pass"""

    test_param_names = list(param_names)
    test_param_names.append("expected")
    test_params_str = ", ".join(test_param_names)
    call_args_str = ", ".join(param_names)

    return f"""import pytest


{solution_body}


@pytest.mark.parametrize(
    "{test_params_str}",
    [
        (/* case 1 */),
    ],
)
def test_{method_name}({test_params_str}):
    assert Solution().{method_name}({call_args_str}) == expected
"""

def main():
    if len(sys.argv) < 3:
        print("Usage: scaffold.py <mode: clipboard|file> <file_path>", file=sys.stderr)
        sys.exit(1)

    mode = sys.argv[1]
    file_path = Path(sys.argv[2]).resolve()
    
    is_java = file_path.suffix == ".java"
    is_python = file_path.suffix == ".py"

    if not is_java and not is_python:
        print(f"Unsupported file type: {file_path}", file=sys.stderr)
        sys.exit(1)

    if mode == "clipboard":
        code = PlatformEnv.get_clipboard_text()
        if not code.strip():
            print("Clipboard is empty!", file=sys.stderr)
            sys.exit(1)
    elif mode == "file":
        if not file_path.exists():
            print(f"File {file_path} does not exist!", file=sys.stderr)
            sys.exit(1)
        code = file_path.read_text(encoding="utf-8")
    else:
        print(f"Unknown mode: {mode}", file=sys.stderr)
        sys.exit(1)

    if is_java:
        parsed = parse_java_solution(code)
        if not parsed:
            parsed = {
                "return_type": "int",
                "method_name": "solve",
                "params": ["int[] nums"],
                "param_names": ["nums"],
                "raw_params": "int[] nums",
                "solution_body": code if "class Solution" in code else ""
            }
        scaffold_text = generate_java_scaffold(file_path, parsed)
    else:
        parsed = parse_python_solution(code)
        if not parsed:
            parsed = {
                "method_name": "solve",
                "params": ["nums: list[int]"],
                "param_names": ["nums"],
                "return_type": "int",
                "solution_body": code if "class Solution" in code else ""
            }
        scaffold_text = generate_python_scaffold(file_path, parsed)

    file_path.parent.mkdir(parents=True, exist_ok=True)
    file_path.write_text(scaffold_text, encoding="utf-8")
    print(f"Successfully generated scaffold for {file_path.name}")

if __name__ == "__main__":
    main()
