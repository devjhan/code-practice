package leetcode.p0424_longest_repeating_character_replacement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int characterReplacement(String s, int k) {
        int left = 0, right = 0;
        int maxFreq = 0, maxWindowSize = 0;
        int[] freqTable = new int[26];

        for (; right < s.length(); ++right) {
            maxFreq = Math.max(maxFreq, ++freqTable[s.charAt(right) - 'A']);

            while (right - left + 1 - maxFreq > k) {
                --freqTable[s.charAt(left++) - 'A'];
            }
            maxWindowSize = Math.max(maxWindowSize, right - left + 1);
        }
        return maxWindowSize;
    }
}

class SlidingWindowTest {
    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of("ABAB", 2, 4),
                Arguments.of("AABABBA", 1, 4),
                Arguments.of("AAAA", 2, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void characterReplacement(String s, int k, int expected) {
        assertThat(new Solution().characterReplacement(s, k)).isEqualTo(expected);
    }
}
