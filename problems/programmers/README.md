# Programmers problems

프로그래머스 문제는 기존 LeetCode와 동일하게 문제 번호 및 풀이 전략별로 분리합니다.

- 경로: `problems/programmers/p17683_just_that_song/simulation/JustThatSongTest.java`
- Java 풀이 `Solution`과 JUnit 5 `@ParameterizedTest`를 같은 파일에 둡니다.
- 기존 Gradle 설정의 `sourceSets.test`가 `problems/`를 포함하므로 별도 빌드 설정 변경은 필요 없습니다.
- 단일 테스트 실행: `./gradlew test --tests 'programmers.p17683_just_that_song.simulation.JustThatSongTest'`
- 문제: [방금그곡 (17683)](https://school.programmers.co.kr/learn/courses/30/lessons/17683)
