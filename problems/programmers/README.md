# Programmers problems

프로그래머스 문제는 기존 LeetCode와 동일하게 문제 번호 및 풀이 전략별로 분리합니다.

- Java 풀이 `Solution`과 JUnit 5 `@ParameterizedTest`를 같은 파일에 둡니다.
- 기존 Gradle 설정의 `sourceSets.test`가 `problems/`를 포함하므로 별도 빌드 설정 변경은 필요 없습니다.

## Problems

- [방금그곡 (17683)](https://school.programmers.co.kr/learn/courses/30/lessons/17683)
  - `p17683_just_that_song/simulation/JustThatSongTest.java`
- [기둥과 보 설치 (60061)](https://school.programmers.co.kr/learn/courses/30/lessons/60061)
  - `p60061_pillar_and_beam_installation/simulation/PillarAndBeamInstallationTest.java`

## Run a single Java test

```bash
./gradlew test --tests 'programmers.p17683_just_that_song.simulation.JustThatSongTest'
./gradlew test --tests 'programmers.p60061_pillar_and_beam_installation.simulation.PillarAndBeamInstallationTest'
```
