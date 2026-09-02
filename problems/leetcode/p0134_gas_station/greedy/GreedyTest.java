package leetcode.p0134_gas_station.greedy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int[] surplus = IntStream.range(0, gas.length).map(i -> gas[i] - cost[i]).toArray();
        if (Arrays.stream(surplus).sum() < 0) return -1;

        int remainedGas = 0;
        int start = 0;
        int looped = 0;

        while (looped < surplus.length) {
            remainedGas = 0;

            for (int i = start; i < surplus.length; ++i) {
                remainedGas += surplus[i];

                if (remainedGas < 0) {
                    start = i + 1;
                    remainedGas = -1;
                    break;
                }
            }

            for (int i = 0; i < start && remainedGas >= 0; ++i) {
                remainedGas += surplus[i];

                if (remainedGas < 0) {
                    start = i + 1;
                    remainedGas = -1;
                    break;
                }
            }
            if (remainedGas >= 0) return start;
            ++looped;
        }
        return -1;
    }
}

class GreedyTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}, 3),
            Arguments.of(new int[]{2, 3, 4}, new int[]{3, 4, 3}, -1),
            Arguments.of(new int[]{5, 1, 2, 3, 4}, new int[]{4, 4, 1, 5, 1}, 4),
            Arguments.of(new int[]{5, 8, 2, 8}, new int[]{6, 5, 6, 6}, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void canCompleteCircuit(int[] gas, int[] cost, int expected) {
        assertThat(new Solution().canCompleteCircuit(gas, cost)).isEqualTo(expected);
    }
}
