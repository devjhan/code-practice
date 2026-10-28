package programmers.p81303_table_editing.bitset_stack;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    BitSet isDeleted;
    Deque<Integer> delStack;
    int ptrAt;

    private int[] parseCmd(String cmd) {
        String[] args = cmd.split(" ");
        int[] parsedCmd = new int[2];

        if (args[0].equals("U")) {
            parsedCmd[0] = 0;
            parsedCmd[1] = Integer.parseInt(args[1]);
        } else if (args[0].equals("D")) {
            parsedCmd[0] = 1;
            parsedCmd[1] = Integer.parseInt(args[1]);
        } else if (args[0].equals("C")) {
            parsedCmd[0] = 2;
        } else {
            parsedCmd[0] = 3;
        }
        return parsedCmd;
    }

    private int getNextNTruePtrAt(int n) {
        int curr = this.ptrAt;

        for (int i = 0; i < n && curr != -1; ++i) {
            curr = this.isDeleted.nextSetBit(curr + 1);
        }
        return curr;
    }

    private int getPrevNTruePtrAt(int n) {
        int curr = this.ptrAt;

        for (int i = 0; i < n && curr != -1; ++i) {
            curr = this.isDeleted.previousSetBit(curr - 1);
        }
        return curr;
    }

    public String solution(int n, int k, String[] cmd) {
        // boolean 배열을 사용하면? 인덱스 맞추기
        // 비트마스킹? 인덱스 맞추기
        // 형식 생각하면 스택도 필요?
        this.isDeleted = new BitSet(n);
        this.isDeleted.set(0, n);
        this.delStack = new ArrayDeque<Integer>();
        this.ptrAt = k;

        for (String op: cmd) {
            int[] operation = parseCmd(op);

            if (operation[0] == 0) {
                int ret = getPrevNTruePtrAt(operation[1]);
                if (ret >= 0) {
                    this.ptrAt = ret;
                }
            } else if (operation[0] == 1) {
                int ret = getNextNTruePtrAt(operation[1]);
                if (ret >= 0) {
                    this.ptrAt = ret;
                }
            } else if (operation[0] == 2) {
                this.isDeleted.clear(this.ptrAt);
                this.delStack.push(this.ptrAt);
                int ret = getNextNTruePtrAt(1);
                if (ret < 0) {
                    int ret2 = getPrevNTruePtrAt(1);
                    if (ret2 >= 0) {
                        this.ptrAt = ret2;
                    }
                } else {
                    this.ptrAt = ret;
                }
            } else {
                int ret = this.delStack.pop();
                this.isDeleted.set(ret);
            }
        }
        StringBuilder answer = new StringBuilder("");
        for (int i = 0; i < n; ++i) {
            if (this.isDeleted.get(i)) {
                answer.append("O");
            } else {
                answer.append("X");
            }
        }
        return answer.toString();
    }
}

class TableEditingTest {
    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(8, 2, new String[] {
                        "D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z"
                }, "OOOOXOOO"),
                Arguments.of(8, 2, new String[] {
                        "D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z", "U 1", "C"
                }, "OOXOXOOO"),
                Arguments.of(5, 2, new String[] {"C"}, "OOXOO"),
                Arguments.of(5, 4, new String[] {"C"}, "OOOOX"),
                Arguments.of(5, 0, new String[] {"C"}, "XOOOO"),
                Arguments.of(5, 2, new String[] {"C", "Z"}, "OOOOO"),
                Arguments.of(6, 2, new String[] {"C", "C", "Z"}, "OOXOOO"),
                Arguments.of(6, 1, new String[] {"C", "D 1", "C"}, "OXOXOO"),
                Arguments.of(5, 1, new String[] {"C", "D 2", "C", "Z", "U 1", "C"}, "OXXOO")
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void tableEditing(int n, int k, String[] commands, String expected) {
        assertThat(new Solution().solution(n, k, commands)).isEqualTo(expected);
    }

    @Test
    void supportsOneMillionRowsAndBoundaryMovement() {
        assertThat(new Solution().solution(1_000_000, 999_999,
                new String[] {"C", "U 1", "C", "Z"}))
                .isEqualTo("O".repeat(999_999) + "X");

        assertThat(new Solution().solution(1_000_000, 0,
                new String[] {"D 300000", "C", "U 299999", "Z"}))
                .isEqualTo("O".repeat(1_000_000));
    }

    @Test
    void randomizedOperationsMatchIndependentListModel() {
        Random random = new Random(81303L);

        for (int trial = 0; trial < 3_000; trial++) {
            int n = 5 + random.nextInt(28);
            int initialCursor = random.nextInt(n);
            int cursor = initialCursor;
            List<Integer> liveRows = new ArrayList<>();
            for (int i = 0; i < n; i++) liveRows.add(i);

            Deque<Integer> deletedRows = new ArrayDeque<>();
            List<String> commands = new ArrayList<>();

            for (int step = 0; step < 100; step++) {
                List<Integer> choices = new ArrayList<>();
                if (cursor > 0) choices.add(0);
                if (cursor < liveRows.size() - 1) choices.add(1);
                if (liveRows.size() > 1) choices.add(2);
                if (!deletedRows.isEmpty()) choices.add(3);

                int action = choices.get(random.nextInt(choices.size()));
                if (action == 0) {
                    int distance = 1 + random.nextInt(cursor);
                    commands.add("U " + distance);
                    cursor -= distance;
                } else if (action == 1) {
                    int distance = 1 + random.nextInt(liveRows.size() - cursor - 1);
                    commands.add("D " + distance);
                    cursor += distance;
                } else if (action == 2) {
                    commands.add("C");
                    deletedRows.push(liveRows.remove(cursor));
                    if (cursor == liveRows.size()) cursor--;
                } else {
                    commands.add("Z");
                    int restored = deletedRows.pop();
                    int selectedRow = liveRows.get(cursor);
                    int insertAt = Collections.binarySearch(liveRows, restored);
                    if (insertAt < 0) insertAt = -insertAt - 1;
                    liveRows.add(insertAt, restored);
                    cursor = liveRows.indexOf(selectedRow);
                }
            }

            char[] expected = new char[n];
            Arrays.fill(expected, 'X');
            for (int row : liveRows) expected[row] = 'O';

            assertThat(new Solution().solution(n, initialCursor,
                    commands.toArray(String[]::new)))
                    .as("randomized trial %s", trial)
                    .isEqualTo(new String(expected));
        }
    }
}
