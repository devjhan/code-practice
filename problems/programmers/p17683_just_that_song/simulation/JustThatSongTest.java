package programmers.p17683_just_that_song.simulation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.*;
import java.time.format.*;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private Duration getRuntimeLength(String startAtStr, String endAtStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startAt = LocalTime.parse(startAtStr, dtf);
        LocalTime endAt = LocalTime.parse(endAtStr, dtf);
        return Duration.between(startAt, endAt);
    }

    private String replacePattern(String s) {
        return s.replace("C#", "c").replace("D#", "d")
                .replace("F#", "f").replace("G#", "g").replace("A#", "a");
    }

    public String solution(String m, String[] musicinfos) {
        String answer = "";
        m = replacePattern(m);
        Duration currDuration = Duration.of(0, ChronoUnit.SECONDS);

        for (int i = 0; i < musicinfos.length; ++i) {
            String[] musicinfo = musicinfos[i].split(",");
            String startAtStr = musicinfo[0];
            String endAtStr = musicinfo[1];
            Duration dur = getRuntimeLength(startAtStr, endAtStr);
            String title = musicinfo[2];
            String pattern;
            musicinfo[3] = replacePattern(musicinfo[3]);
            if ((int) dur.toMinutes() > musicinfo[3].length()) {
                pattern = musicinfo[3]
                        .repeat((int) Math.ceil((double) dur.toMinutes() / musicinfo[3].length()))
                        .substring(0, (int) dur.toMinutes());
            } else {
                pattern = musicinfo[3].substring(0, (int) dur.toMinutes());
            }

            if (pattern.contains(m)) {
                if (currDuration.compareTo(dur) < 0) {
                    answer = title;
                    currDuration = dur;
                } else if (currDuration.compareTo(dur) == 0) {
                    // 재생 시간이 같다면 musicinfos에 먼저 등장한 곡을 유지한다.
                }
            }
        }
        return answer.isEmpty() ? "(None)" : answer;
    }
}

class JustThatSongTest {
    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of("ABCDEFG", new String[]{
                        "12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"}, "HELLO"),
                Arguments.of("CC#BCC#BCC#BCC#B", new String[]{
                        "03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"}, "FOO"),
                Arguments.of("ABC", new String[]{
                        "12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF"}, "WORLD"),
                Arguments.of("ABC", new String[]{
                        "12:00,12:02,TOO_SHORT,ABC"}, "(None)"),
                Arguments.of("CAB", new String[]{
                        "12:00,12:05,WRAP,ABC"}, "WRAP"),
                Arguments.of("C", new String[]{
                        "12:00,12:05,SHARP_ONLY,C#"}, "(None)"),
                Arguments.of("C#", new String[]{
                        "12:00,12:01,SHARP,C#", "12:00,12:01,PLAIN,C"}, "SHARP"),
                Arguments.of("A", new String[]{
                        "13:00,13:02,X,A", "09:00,09:02,MUCH_LONGER_TITLE,A"}, "X"),
                Arguments.of("ABC", new String[]{
                        "12:00,12:03,FIRST,ABC", "13:00,13:06,LONGER,ABC"}, "LONGER"),
                Arguments.of("A", new String[]{
                        "12:00,12:00,ZERO,A"}, "(None)"),
                Arguments.of("A#", new String[]{
                        "12:00,12:01,SHARP_A,A#", "13:00,13:01,PLAIN_A,A"}, "SHARP_A"),
                Arguments.of("F#G#", new String[]{
                        "12:00,12:02,SHARPS,F#G#"}, "SHARPS")
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void justThatSong(String melody, String[] musicInfos, String expected) {
        assertThat(new Solution().solution(melody, musicInfos)).isEqualTo(expected);
    }
}
