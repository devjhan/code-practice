package leetcode.p0347_top_k_frequent_elements;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        int[] valueToFrequency = new int[20001];
        List[] frequencyToValuesTable = new ArrayList[nums.length + 1];

        for (int i = 0; i < nums.length + 1; ++i) {
            frequencyToValuesTable[i] = new ArrayList<Integer>();
        }

        for (int num : nums) {
            ++valueToFrequency[num + 10000];
        }

        for (int i = 0; i < 20001; ++i) {
            if (valueToFrequency[i] > 0) {
                frequencyToValuesTable[valueToFrequency[i]].add(i - 10000);
            }
        }

        int answerIndex = 0;
        int[] answer = new int[k];

        for (int i = nums.length; i >= 0; --i) {
            for (int j = 0; j < frequencyToValuesTable[i].size() && answerIndex < k; ++j) {
                answer[answerIndex++] = (int) frequencyToValuesTable[i].get(j);
            }
        }
        return answer;
    }
}

class ArrayDictionaryTest {
    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new int[] {1, 1, 1, 2, 2, 3}, 2, new int[] {1, 2}),
                Arguments.of(new int[] {1}, 1, new int[] {1}),
                Arguments.of(new int[] {1, 2, 1, 2, 1, 2, 3, 1, 3, 2}, 2, new int[] {1, 2}));
    }

    @ParameterizedTest
    @MethodSource("cases")
    void topKFrequent(int[] nums, int k, int[] expected) {
        assertThat(new Solution().topKFrequent(nums, k)).containsExactlyInAnyOrder(expected);
    }
}
