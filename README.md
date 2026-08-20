# Code Practice

코딩 테스트 문제를 문제·플랫폼 단위로 정리하고 Python과 Java 풀이를 독립적으로 검증하는 저장소입니다.

## 준비

### 1. Python 가상환경 구성
* **macOS / Linux**:
  ```bash
  python3 -m venv .venv
  .venv/bin/pip install -r requirements.txt
  ```
* **Windows (PowerShell)**:
  ```powershell
  python -m venv .venv
  .\.venv\Scripts\pip.exe install -r requirements.txt
  ```

### 2. Java 개발 환경
* Gradle Wrapper와 JDK 17 이상을 사용하며, AssertJ 및 JUnit 5 기반으로 검증됩니다.
* Windows와 macOS/Linux 모두 별도 설치 없이 번들된 Gradle Wrapper(`gradlew` / `gradlew.bat`)로 자동 구동됩니다.

## 테스트 실행

### 1. CLI 테스트 실행
* **공통 (OS 무관)**:
  ```bash
  python scripts/test_runner.py --all      # 전체 테스트 (Python + Java)
  python scripts/test_runner.py --python   # Python 전체 테스트
  python scripts/test_runner.py --java     # Java 전체 테스트
  ```
* **macOS / Linux (Make / Gradle)**:
  ```bash
  make test         # 전체 테스트
  make test-python  # Python 전체 테스트
  make test-java    # Java 전체 테스트
  ./gradlew test    # Gradle Java 테스트
  ```
* **Windows (Gradle Batch)**:
  ```powershell
  .\gradlew.bat test  # Gradle Java 테스트
  ```


### 2. Zed Tasks 단축키 (`Cmd + Shift + R` / `task: spawn`)
Zed 에디터에서 `Cmd + Shift + R`을 누르고 LeetCode 태스크를 즉시 실행할 수 있습니다:

#### 1) 문제 스캐폴딩 및 자동 생성 (LeetCode 전용)
* **`LeetCode: Scaffold from Clipboard`**:
  * LeetCode 웹에서 복사(`Cmd + C`)한 `class Solution` 코드로부터 **패키지명, 테스트 클래스명, 파라미터 시그니처, 최적 AssertJ 단언문(`containsExactlyInAnyOrderElementsOf`, `isEqualTo` 등)**을 100% 자동 추론하여 현재 파일에 생성합니다.
* **`LeetCode: Generate Test from Solution`**:
  * 현재 파일에 작성/붙여넣기된 `class Solution` 코드를 분석하여 하단에 파라미터화 테스트 클래스를 자동 생성합니다.

#### 2) 테스트 실행 태스크
* **`LeetCode: Test Current File`**: 현재 열려있는 파일(`.java` 또는 `.py`)의 테스트만 격리 실행
* **`LeetCode: Test All (Java & Python)`**: 전체 테스트 실행 (`make test`)
* **`LeetCode: Test All Java`**: Java 전체 테스트 실행 (`./gradlew test`)
* **`LeetCode: Test All Python`**: Python 전체 테스트 실행 (`.venv/bin/pytest -v`)

## 문제 추가 규칙 (0-Step Setup)

문제는 `problems/<platform>/<p번호_이름>/` 아래에 둡니다.

```text
problems/
  leetcode/
    p0001_two_sum/
      hash_table/
        TwoSumTest.java
      test_hashtable.py
      test_bruteforce.py
    p0215_kth_largest_element_in_an_array/
      min_heap/
        MinHeapTest.java
      quick_select/
        QuickSelectTest.java
```

### 1. Zero-Step LSP 지원
* 별도의 `build.gradle.kts` 생성, `settings.gradle.kts` 수정, Gradle 동기화 절차가 **전혀 필요 없습니다**.
* 파일을 생성하자마자 Zed LSP(JDTLS / Basedpyright)가 즉시 구문 강조, 타입 힌트, 자동완성을 제공합니다.

### 2. 보조 코드 스니펫 (Zed Snippets)

* **macOS / Linux**:
  ```bash
  ln -sf $(pwd)/.zed/snippets/java.json ~/.config/zed/snippets/java.json
  ln -sf $(pwd)/.zed/snippets/python.json ~/.config/zed/snippets/python.json
  ```
* **Windows (PowerShell)**:
  ```powershell
  New-Item -ItemType SymbolicLink -Path "$env:APPDATA\Zed\snippets\java.json" -Target "$PWD\.zed\snippets\java.json" -Force
  New-Item -ItemType SymbolicLink -Path "$env:APPDATA\Zed\snippets\python.json" -Target "$PWD\.zed\snippets\python.json" -Force
  ```

* **`algo:cases`**: `@MethodSource("cases")` (Java) / `@pytest.mark.parametrize` (Python) 케이스 메서드 생성
* **`algo:assert-order`**: `assertThat(actual).containsExactlyInAnyOrder(expected);` (순서 무관 배열/컬렉션 검증)
* **`algo:assert-elements`**: `assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);` (순서 무관 중첩 컬렉션 검증)

### 3. Java 풀이 전략 폴더 및 패키지 규칙
* **단일/복수 풀이 공통 규칙**: 모든 Java 풀이는 항상 문제 폴더 아래의 `{전략_이름_폴더}`에 배치합니다:
  ```text
  problems/leetcode/p0001_two_sum/hash_table/TwoSumTest.java
  ➔ package leetcode.p0001_two_sum.hash_table;

  problems/leetcode/p0215_kth_largest_element_in_an_array/min_heap/MinHeapTest.java
  ➔ package leetcode.p0215_kth_largest_element_in_an_array.min_heap;
  ```
* 각 전략 파일은 독립된 최상위 `class Solution`을 가질 수 있으며 패키지 네임스페이스 격리로 인해 충돌하지 않습니다.
* 단일 파일 테스트 실행 시 FQCN(전체 패키지명)으로 전달되어, 동일한 테스트 클래스명을 가진 다른 문제와 완벽히 격리되어 실행됩니다.



