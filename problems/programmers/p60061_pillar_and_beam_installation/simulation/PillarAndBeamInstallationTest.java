package programmers.p60061_pillar_and_beam_installation.simulation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    int[][] map;
    int n;

    // 00 -> VOID
    // 01 -> COL
    // 10 -> BEAM
    // 11 -> BOTH
    private boolean checkInvariants(int[] ops) {
        int[][] tmp = new int[this.n][this.n];
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < this.n; ++j) {
                tmp[i][j] = map[i][j];
            }
        }

        int r = ops[0], c = ops[1], type = ops[2], operator = ops[3];
        if (operator == 0) tmp[c][r] &= ~(1 << type);
        else tmp[c][r] |= (1 << type);

        // i == 0 케이스 확인
        for (int j = 0; j < n; ++j) {
            if ((tmp[0][j] & (1 << 1)) != 0) return false;
        }

        for (int i = 1; i < n; ++i) {
            // j == 0 케이스 확인
            if (((tmp[i][0] & (1 << 0)) != 0)
                    && ((tmp[i - 1][0] & (1 << 0)) == 0)
                    && ((tmp[i][0] & (1 << 1)) == 0)) return false;

            if ((tmp[i][0] & 2) != 0) {
                if ((tmp[i - 1][0] & 1) == 0
                        && (tmp[i - 1][1] & 1) == 0) {
                    return false;
                }
            }

            for (int j = 1; j < n - 1; ++j) {
                if ((tmp[i][j] & (1 << 0)) != 0) {
                    if (((tmp[i - 1][j] & (1 << 0)) == 0)
                            && ((tmp[i][j] & (1 << 1)) == 0)
                            && ((tmp[i][j - 1] & (1 << 1)) == 0)) return false;
                }

                if ((tmp[i][j] & 2) != 0) {
                    boolean pillarSupport =
                            ((tmp[i - 1][j] & 1) != 0)
                                    || ((tmp[i - 1][j + 1] & 1) != 0);

                    boolean beamSupport =
                            ((tmp[i][j - 1] & 2) != 0)
                                    && ((tmp[i][j + 1] & 2) != 0);

                    if (!pillarSupport && !beamSupport) {
                        return false;
                    }
                }
            }

            // j == n - 1 케이스 확인
            // j == n - 1: 오른쪽 끝에는 보를 설치할 수 없음
            if ((tmp[i][n - 1] & 2) != 0) {
                return false;
            }

            // 맨 오른쪽 기둥은 아래 기둥 또는 왼쪽 보가 지지
            if ((tmp[i][n - 1] & 1) != 0) {
                if ((tmp[i - 1][n - 1] & 1) == 0
                        && (tmp[i][n - 2] & 2) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] solution(int n, int[][] build_frame) {
        List<int[]> answer = new ArrayList<int[]>();
        this.n = n + 1;
        this.map = new int[this.n][this.n];

        for (int[] ops : build_frame) {
            if (checkInvariants(ops)) {
                if (ops[3] == 0) map[ops[1]][ops[0]] &= ~(1 << ops[2]);
                else map[ops[1]][ops[0]] |= (1 << ops[2]);
            }
        }

        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < this.n; ++j) {
                if ((map[j][i] & (1 << 0)) != 0) {
                    answer.add(new int[] {i, j, 0});
                }
                if ((map[j][i] & (1 << 1)) != 0) {
                    answer.add(new int[] {i, j, 1});
                }
            }
        }

        int[][] ans = answer.stream().toArray(int[][]::new);
        return ans;
    }
}

class PillarAndBeamInstallationTest {
    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(5, new int[][] {
                        {1,0,0,1}, {1,1,1,1}, {2,1,0,1}, {2,2,1,1},
                        {5,0,0,1}, {5,1,0,1}, {4,2,1,1}, {3,2,1,1}
                }, new int[][] {
                        {1,0,0}, {1,1,1}, {2,1,0}, {2,2,1},
                        {3,2,1}, {4,2,1}, {5,0,0}, {5,1,0}
                }),
                Arguments.of(5, new int[][] {
                        {0,0,0,1}, {2,0,0,1}, {4,0,0,1},
                        {0,1,1,1}, {1,1,1,1}, {2,1,1,1},
                        {3,1,1,1}, {2,0,0,0}, {1,1,1,0}, {2,2,0,1}
                }, new int[][] {
                        {0,0,0}, {0,1,1}, {1,1,1}, {2,1,1}, {3,1,1}, {4,0,0}
                }),
                Arguments.of(5, new int[][] {{1,1,0,1}}, new int[][] {}),
                Arguments.of(5, new int[][] {{5,0,0,1}}, new int[][] {{5,0,0}}),
                Arguments.of(5, new int[][] {
                        {0,0,0,1}, {0,1,1,1}
                }, new int[][] {{0,0,0}, {0,1,1}}),
                Arguments.of(5, new int[][] {
                        {5,0,0,1}, {4,1,1,1}
                }, new int[][] {{4,1,1}, {5,0,0}}),
                Arguments.of(5, new int[][] {
                        {1,0,0,1}, {1,1,0,1}, {1,0,0,0}
                }, new int[][] {{1,0,0}, {1,1,0}}),
                Arguments.of(5, new int[][] {
                        {0,0,0,1}, {2,0,0,1}, {0,1,1,1},
                        {2,1,1,1}, {1,1,1,1}
                }, new int[][] {
                        {0,0,0}, {0,1,1}, {1,1,1}, {2,0,0}, {2,1,1}
                }),
                Arguments.of(5, new int[][] {
                        {0,0,0,1}, {0,1,0,1}, {0,1,1,1}
                }, new int[][] {{0,0,0}, {0,1,0}, {0,1,1}}),
                Arguments.of(5, new int[][] {{0,0,1,1}}, new int[][] {}),
                Arguments.of(5, new int[][] {{5,1,1,1}}, new int[][] {})
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void pillarAndBeamInstallation(int n, int[][] buildFrame, int[][] expected) {
        int[][] actual = new Solution().solution(n, buildFrame);
        assertThat(Arrays.deepToString(actual)).isEqualTo(Arrays.deepToString(expected));
    }
}
