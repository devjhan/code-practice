package programmers.p17676_thanksgiving_traffic.interval_overlap;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.time.format.*;
import java.time.*;
import java.time.temporal.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int solution(String[] lines) {
        int maxCount = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss.SSS");

        for (int i = 0; i < lines.length; ++i) {
            StringBuilder temp = new StringBuilder(lines[i]);
            temp.setCharAt(10, '-');
            lines[i] = temp.toString();
        }

        for (int i = 0; i < lines.length; ++i) {
            String[] tokens = lines[i].split(" ");
            final long to = dtf.parse(tokens[0], LocalDateTime::from).toInstant(ZoneOffset.UTC).toEpochMilli();
            final long delta = (long) Math.round(Double.parseDouble(tokens[1].substring(0, tokens[1].length() - 1)) * 1000L);
            int count = 0;
            final long from = to - 999L;

            for (int j = 0; j < lines.length; ++j) {
                String[] tokens2 = lines[j].split(" ");
                final long to2 = dtf.parse(tokens2[0], LocalDateTime::from).toInstant(ZoneOffset.UTC).toEpochMilli();
                final long delta2 = Math.round(Double.parseDouble(tokens2[1].substring(0, tokens2[1].length() - 1)) * 1000L);;
                final long from2 = to2 - delta2 + 1L;
                if (from2 <= to + 999L && to <= to2) ++count;
            }
            maxCount = Math.max(maxCount, count);
        }
        return maxCount;
    }
}

class ThanksgivingTrafficTest {
    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new String[]{
                        "2016-09-15 01:00:04.001 2.0s",
                        "2016-09-15 01:00:07.000 2s"
                }, 1),
                Arguments.of(new String[]{
                        "2016-09-15 01:00:04.002 2.0s",
                        "2016-09-15 01:00:07.000 2s"
                }, 2),
                Arguments.of(new String[]{
                        "2016-09-15 00:00:00.000 0.001s"
                }, 1),
                Arguments.of(new String[]{
                        "2016-09-15 23:59:59.999 3s"
                }, 1),
                Arguments.of(new String[]{
                        "2016-09-15 01:00:00.000 0.001s",
                        "2016-09-15 01:00:01.000 0.001s"
                }, 1),
                Arguments.of(new String[]{
                        "2016-09-15 01:00:00.000 0.001s",
                        "2016-09-15 01:00:00.999 0.001s"
                }, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void countsOverlappingOneSecondWindows(String[] lines, int expected) {
        assertThat(new Solution().solution(lines.clone())).isEqualTo(expected);
    }

    private static String line(int endMillis, int durationMillis) {
        String time = LocalTime.ofSecondOfDay(endMillis / 1000)
                .plusNanos((endMillis % 1000) * 1_000_000L)
                .format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        String duration = (durationMillis / 1000)
                + "." + String.format(Locale.ROOT, "%03d", durationMillis % 1000) + "s";
        return "2016-09-15 " + time + " " + duration;
    }

    // Brute-force every millisecond as a possible 1-second window start.
    private static int bruteForceOracle(int[][] intervals) {
        int earliest = Integer.MAX_VALUE;
        int latest = Integer.MIN_VALUE;
        for (int[] interval : intervals) {
            earliest = Math.min(earliest, interval[0]);
            latest = Math.max(latest, interval[1]);
        }

        int best = 0;
        for (int start = earliest - 999; start <= latest; start++) {
            int count = 0;
            for (int[] interval : intervals) {
                if (interval[0] <= start + 999 && interval[1] >= start) {
                    count++;
                }
            }
            best = Math.max(best, count);
        }
        return best;
    }

    @Test
    void seededRandomizedCasesMatchMillisecondOracle() {
        Random random = new Random(17676L);
        for (int trial = 0; trial < 600; trial++) {
            int count = 1 + random.nextInt(25);
            int[][] intervals = new int[count][2];
            String[] lines = new String[count];

            for (int i = 0; i < count; i++) {
                int duration = 1 + random.nextInt(3000);
                int end = 4000 + random.nextInt(6000);
                intervals[i][0] = end - duration + 1;
                intervals[i][1] = end;
                lines[i] = line(end, duration);
            }
            assertThat(new Solution().solution(lines.clone()))
                    .as("randomized trial %s", trial)
                    .isEqualTo(bruteForceOracle(intervals));
        }
    }

    @Test
    void supportsTwoThousandOverlappingLogs() {
        String[] lines = new String[2_000];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = line(4000 + i, 3000);
        }
        assertThat(new Solution().solution(lines)).isEqualTo(2_000);
    }
}
