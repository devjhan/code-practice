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
에디터에서 `ps`를 입력하고 `Tab`을 누르면 기본 템플릿이 자동 생성됩니다:
* **Java**: `package leetcode.pXXXX_name;`, `Solution` 클래스, AssertJ `assertThat`, JUnit 5 `@ParameterizedTest` + `cases()` 템플릿 생성
* **Python**: `Solution` 클래스, `@pytest.mark.parametrize` + `test_solution` 템플릿 생성
* **단언문 스니펫 (Java)**:
  * `cases`: `@MethodSource("cases")` 메서드 생성
  * `assert-order`: `assertThat(actual).containsExactlyInAnyOrder(expected);` (순서 무관 배열/컬렉션 검증)
  * `assert-elements`: `assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);` (순서 무관 중첩 컬렉션 검증)

### 3. Java 패키지 및 클래스 작성 규칙
* Java 파일 상단에는 디렉토리 경로에 맞게 `package leetcode.pXXXX_name;`을 선언합니다.
* 단일 문제에 복수 풀이 전략이 있는 경우(`MinHeapTest.java`, `QuickSelectTest.java`), 테스트 클래스 내부에 `static class Solution`을 두어 동일 패키지 내 충돌 없이 독립적으로 작성합니다.

