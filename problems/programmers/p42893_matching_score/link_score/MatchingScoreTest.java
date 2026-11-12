package programmers.p42893_matching_score.link_score;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public int solution(String word, String[] pages) {
        // return i of pages: 매칭점수 DESC, i ASC TOP 1
        // 매칭점수 = 기본점수 + 링크 점수
        // - 기본점수: word와 단어 단위 일치 / 대소문자 무시,
        //  - 단어 단위: 알파벳을 제외한 나머지 문자는 전부 공백 취급
        // - 링크 점수(double):
        //  - self: <meta property=""... content=
        final double EPS = 1e-9;
        int[] basicScores = new int[pages.length];
        String[] self = new String[pages.length];
        Map<Integer, Integer>[] refLinks = new HashMap[pages.length];
        int[] inNodesCounter = new int[pages.length];
        int[] outNodesCounter = new int[pages.length];
        double[] scores = new double[pages.length];

        word = word.toLowerCase();

        for (int i = 0; i < pages.length; ++i) {
            refLinks[i] = new HashMap<Integer, Integer>();
            pages[i] = pages[i].toLowerCase();
        }

        for (int i = 0; i < pages.length; ++i) {
            final int selfPtr = pages[i].indexOf("<meta property=\"og:url\"");
            final int selfContentPtr = pages[i].indexOf("https://", selfPtr);
            final int selfContentEndPtr = pages[i].indexOf("\"", selfContentPtr);
            self[i] = pages[i].substring(selfContentPtr, selfContentEndPtr);
        }

        for (int i = 0; i < pages.length; ++i) {
            final int bodyStartsAt = pages[i].indexOf("<body>");
            final int bodyEndsAt = pages[i].indexOf("</body>");
            int ahrefStartsAt = pages[i].indexOf("<a href=\"", bodyStartsAt);

            while (ahrefStartsAt != -1 && ahrefStartsAt < bodyEndsAt) {
                final int urlStartsAt = ahrefStartsAt + 9;
                final int ahrefEndsAt = pages[i].indexOf("\"", urlStartsAt);
                if (ahrefEndsAt == -1) break;
                final String link = pages[i].substring(urlStartsAt, ahrefEndsAt);
                ++outNodesCounter[i];
                for (int j = 0; j < pages.length; ++j) {
                    if (self[j].equals(link)) {
                        ++inNodesCounter[j];
                        refLinks[j].putIfAbsent(i, 0);
                        refLinks[j].put(i, refLinks[j].get(i) + 1);
                        break;
                    }
                }
                ahrefStartsAt = pages[i].indexOf("<a href=\"", ahrefEndsAt);
            }
        }

        for (int i = 0; i < pages.length; ++i) {
            final int bodyStartsAt = pages[i].indexOf("<body>");
            final int bodyEndsAt = pages[i].indexOf("</body>");
            int keywordStartsAt = bodyStartsAt + 5;

            while (keywordStartsAt != -1 && keywordStartsAt <= bodyEndsAt) {
                keywordStartsAt = pages[i].indexOf(word, keywordStartsAt + 1);
                if (keywordStartsAt == -1 || keywordStartsAt > bodyEndsAt) break;
                final int keywordEndsAt = keywordStartsAt + word.length();
                if (keywordEndsAt <= bodyEndsAt && !Character.isAlphabetic(pages[i].charAt(keywordEndsAt)) && !Character.isAlphabetic(pages[i].charAt(keywordStartsAt - 1))) {
                    ++basicScores[i];
                }
            }
        }
        for (int i = 0; i < pages.length; ++i) {
            double refScoreSum = 0;
            for (Map.Entry<Integer, Integer> idx: refLinks[i].entrySet()) {
                refScoreSum += idx.getValue() * ((double) basicScores[idx.getKey()]) / outNodesCounter[idx.getKey()];
            }
            scores[i] = (double) basicScores[i] + refScoreSum;
        }
        int answerIdx = 0;
        double maxSum = -1.0;

        for (int i = 0; i < pages.length; ++i) {
            if (scores[i] > maxSum + EPS) {
                maxSum = scores[i];
                answerIdx = i;
            }
        }
        return answerIdx;
    }
}

class MatchingScoreTest {
    private static String page(String url, String body, String... links) {
        StringBuilder html = new StringBuilder("<html><head><meta property=\"og:url\" content=\"https://")
                .append(url).append("\"/></head><body>").append(body);
        for (String link : links) {
            html.append("<a href=\"https://").append(link).append("\">go</a>");
        }
        return html.append("</body></html>").toString();
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of("blind", new String[] {
                        page("a.test", "Blind!! blind1 blindspot aBLIND blind."),
                        page("b.test", "blind blind blind blind")
                }, 1),
                Arguments.of("blind", new String[] {
                        page("a.test", "blind"), page("b.test", "BLIND")
                }, 0),
                Arguments.of("blind", new String[] {
                        page("a.test", "blind blind", "b.test", "b.test"),
                        page("b.test", "blind")
                }, 1),
                Arguments.of("blind", new String[] {
                        page("a.test", "blind", "b.test", "outside.test"),
                        page("b.test", "blind")
                }, 1),
                Arguments.of("blind", new String[] {
                        page("a.test", "1blind2 blind_ BLIND3"),
                        page("b.test", "unblind")
                }, 0),
                Arguments.of("blind", new String[] {
                        page("a.test", "notfound"), page("b.test", "anotherword")
                }, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void countsBasicAndLinkScores(String word, String[] pages, int expected) {
        assertThat(new Solution().solution(word, pages.clone())).isEqualTo(expected);
    }

    @Test
    void mathematicallyEqualScoresKeepEarlierPageDespiteFloatingPointRoundoff() {
        String[] pages = {
                page("a.test", "blind"),
                page("b.test", "blind"),
                page("c.test", "blind", "a.test", "outside1.test", "outside2.test"),
                page("d.test", "blind", "a.test", "outside3.test", "outside4.test", "outside5.test"),
                page("e.test", "blind", "b.test", "outside6.test"),
                page("f.test", "blind", "b.test", "outside7.test", "outside8.test", "outside9.test",
                        "outside10.test", "outside11.test", "outside12.test", "outside13.test",
                        "outside14.test", "outside15.test", "outside16.test", "outside17.test")
        };
        // Both pages 0 and 1 have exactly 1 + 7/12 mathematically.
        assertThat(new Solution().solution("blind", pages)).isZero();
    }
}
