package leetcode.p0560_subarray_sum_equals_k;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> prefixSumCounter = new HashMap<>();
        int[] prefixSums = new int[nums.length + 1];
        int answer = 0;

        for (int i = 1; i <= nums.length; ++i) {
            prefixSums[i] = prefixSums[i - 1] + nums[i - 1];
        }

        prefixSumCounter.put(0, 1);

        for (int i = 1; i < prefixSums.length; ++i) {
            answer += prefixSumCounter.getOrDefault(prefixSums[i] - k, 0);
            prefixSumCounter.put(prefixSums[i], prefixSumCounter.getOrDefault(prefixSums[i], 0) + 1);
        }
        return answer;
    }
}

class PrefixSumTest {
    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new int[] {1, 1, 1}, 2, 2),
                Arguments.of(new int[] {1, 2, 3}, 3, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void subarraySum(int[] nums, int k, int expected) {
        assertThat(new Solution().subarraySum(nums, k)).isEqualTo(expected);
    }
}
