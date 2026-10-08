package leetcode.p0240_search_a_2d_matrix_ii.compressing_search_space;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int i = 0, j = matrix[0].length - 1;

        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] > target) --j;
            else if (matrix[i][j] < target) ++i;
            else return true;
        }
        return false;
    }
}

class CompressingSearchSpaceTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[][]{{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}}, 5, true),
            Arguments.of(new int[][]{{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}}, 20, false)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void searchMatrix(int[][] matrix, int target, boolean expected) {
        assertThat(new Solution().searchMatrix(matrix, target)).isEqualTo(expected);
    }
}
