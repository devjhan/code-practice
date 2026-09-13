package leetcode.p0091_decode_ways.dynamic_programming;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    private static boolean isDecodable(String s, int i, boolean isSolo) {
        if (isSolo) return s.charAt(i) != '0';

        String temp = new StringBuilder().append(s.charAt(i)).append(s.charAt(i + 1)).toString();
        return !temp.startsWith("0") && Integer.parseInt(temp) <= 26;
    }
    public int numDecodings(String s) {
        int curr = 0, prev = isDecodable(s, 0, true) ? 1 : 0, prev2 = 1;

        for (int i = 1; i < s.length(); i++) {
            curr = (isDecodable(s, i, true) ? prev : 0) + (isDecodable(s, i - 1, false) ? prev2 : 0);
            prev2 = prev;
            prev = curr;
        }
        return s.length() > 1 ? curr : prev;
    }
}

class DynamicProgrammingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of("12", 2),
            Arguments.of("226", 3),
            Arguments.of("06", 0),
            Arguments.of("0", 0),
            Arguments.of("1", 1),
            Arguments.of("2101", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void numDecodings(String s, int expected) {
        assertThat(new Solution().numDecodings(s)).isEqualTo(expected);
    }
}
