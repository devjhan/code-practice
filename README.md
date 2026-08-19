# Code Practice

코딩 테스트 문제를 문제·플랫폼 단위로 정리하고 Python과 Java 풀이를 독립적으로 검증하는 저장소입니다.

## 준비

Python 테스트는 가상환경에 의존합니다.

```bash
python3 -m venv .venv
.venv/bin/pip install -r requirements.txt
```

Java 테스트는 Gradle Wrapper와 JDK 17 이상을 사용하며, AssertJ 및 JUnit 5 기반으로 검증됩니다.

## 테스트 실행

### 1. CLI 테스트 실행
```bash
make test         # Python + Java 전체 테스트
make test-python  # Python 전체 테스트
make test-java    # Java 전체 테스트
./gradlew test    # Gradle Java 테스트
```

### 2. Zed Tasks 단축키 (`Cmd + Shift + R` / `task: spawn`)
Zed 에디터에서 `Cmd + Shift + R`을 누르고 태스크를 선택하거나 재실행할 수 있습니다:
* **`Test: Current File`**: 현재 열려있는 파일(`.java` 또는 `.py`)의 테스트만 격리 실행
* **`Test: All (Java & Python)`**: 전체 테스트 실행
* **`Test: All Java`**: Java 전체 테스트 실행
* **`Test: All Python`**: Python 전체 테스트 실행

## 문제 추가 규칙 (0-Step Setup)

문제는 `problems/<platform>/<p번호_이름>/` 아래에 둡니다.

```text
problems/
  leetcode/
    p0001_two_sum/
      TwoSumTest.java
      test_hashtable.py
      test_bruteforce.py
```

### 1. Zero-Step LSP 지원
* 별도의 `build.gradle.kts` 생성, `settings.gradle.kts` 수정, Gradle 동기화 절차가 **전혀 필요 없습니다**.
* 파일을 생성하자마자 Zed LSP(JDTLS / Basedpyright)가 즉시 구문 강조, 타입 힌트, 자동완성을 제공합니다.

### 2. 코드 스니펫 (Zed Snippets)

Zed의 글로벌 스니펫 디렉토리(`~/.config/zed/snippets/`)로 심볼릭 링크를 연결하여 사용합니다:

```bash
ln -sf $(pwd)/.zed/snippets/java.json ~/.config/zed/snippets/java.json
ln -sf $(pwd)/.zed/snippets/python.json ~/.config/zed/snippets/python.json
```

LSP 기본 키워드와의 충돌을 방지하기 위해 `algo:` 접두사를 사용합니다:
* **`algo:ps`**:
  * **Java**: `package leetcode.pXXXX_name;`, `Solution` 클래스, AssertJ `assertThat`, JUnit 5 `@ParameterizedTest` + `cases()` 템플릿 생성
  * **Python**: `Solution` 클래스, `@pytest.mark.parametrize` + `test_solution` 템플릿 생성
* **`algo:cases`**: `@MethodSource("cases")` (Java) / `@pytest.mark.parametrize` (Python) 케이스 생성
* **`algo:assert-order`**: `assertThat(actual).containsExactlyInAnyOrder(expected);` (순서 무관 배열/컬렉션 검증)
* **`algo:assert-elements`**: `assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);` (순서 무관 중첩 컬렉션 검증)


### 3. Java 패키지 및 복수 풀이(전략) 폴더 규칙
* **단일 풀이**: `problems/<platform>/<p번호_이름>/` 바로 아래에 `*Test.java`를 두고, 상단에 `package leetcode.pXXXX_name;`을 선언합니다.
* **복수 풀이 (동일 언어)**: 문제 폴더 아래에 `{전략_이름_폴더}`를 두고, 별개 패키지로 분리합니다:
  ```text
  problems/leetcode/p0215_kth_largest_element_in_an_array/
    ├── min_heap/
    │    └── MinHeapTest.java          # package leetcode.p0215_kth_largest_element_in_an_array.min_heap;
    └── quick_select/
         └── QuickSelectTest.java      # package leetcode.p0215_kth_largest_element_in_an_array.quick_select;
  ```
  * 각 전략 파일은 독립된 최상위 `class Solution`을 가질 수 있으며 패키지 네임스페이스 격리로 인해 충돌하지 않습니다.


