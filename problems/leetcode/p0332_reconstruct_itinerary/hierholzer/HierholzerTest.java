package leetcode.p0332_reconstruct_itinerary.hierholzer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, List<String>> adjacencyList = new HashMap<>();
        List<String> itinerary = new LinkedList<>();

        for (List<String> ticket: tickets) {
            adjacencyList.computeIfAbsent(ticket.get(0), k -> new LinkedList<>()).add(ticket.get(1));
        }
        adjacencyList.values().forEach(Collections::sort);
        dfs("JFK", itinerary, adjacencyList);
        return itinerary;
    }

    private void dfs(String from, List<String> itinerary, Map<String, List<String>> adjacencyList) {
        List<String> adjacentAirports = adjacencyList.get(from);
        while (adjacentAirports != null && !adjacentAirports.isEmpty()) {
            String to = adjacentAirports.remove(0);
            dfs(to, itinerary, adjacencyList);
        }
        itinerary.add(0, from);
    }
}

class HierholzerTest {
    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(
                List.of(
                    List.of("MUC", "LHR"),
                    List.of("JFK", "MUC"),
                    List.of("SFO", "SJC"),
                    List.of("LHR", "SFO")
                ),
                List.of("JFK", "MUC", "LHR", "SFO", "SJC")
            )
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void findItinerary(List<List<String>> tickets, List<String> expected) {
        assertThat(new Solution().findItinerary(tickets)).containsExactlyInAnyOrderElementsOf(expected);
    }
}
