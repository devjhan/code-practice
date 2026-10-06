package leetcode.p0049_group_anagrams.array_dictionary;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Wrapper {
    private final int[] freq;

    Wrapper(String s) {
        this.freq = new int[26];
        for (char c : s.toCharArray()) {
            this.freq[c - 'a']++;
        }
    }

    @Override
    public boolean equals(Object a) {
        if (this == a) return true;
        if (!(a instanceof Wrapper)) return false;
        Wrapper that = (Wrapper) a;
        return Arrays.equals(this.freq, that.freq);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(freq);
    }
}
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<Wrapper, List<String>> groups = new HashMap<>();
        List<List<String>> ret = new ArrayList<>();

        for (String s: strs) {
            groups.computeIfAbsent(new Wrapper(s), k -> new ArrayList<>()).add(s);
        }

        for (Entry<Wrapper, List<String>> entry: groups.entrySet()) {
            ret.add(entry.getValue());
        }

        return ret;
    }
}

class ArrayDictionaryTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}, List.of(List.of("eat", "tea", "ate"), List.of("tan", "nat"), List.of("bat")))
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void groupAnagrams(String[] strs, List<List<String>> expected) {
        assertThat(new Solution().groupAnagrams(strs)).containsExactlyInAnyOrderElementsOf(expected);
    }
}
