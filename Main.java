import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, List<List<String>>> grammar = new HashMap<>();
        grammar.put("E", Arrays.asList(Arrays.asList("T", "E'")));
        grammar.put("E'", Arrays.asList(Arrays.asList("+", "T", "E'"), Arrays.asList("ε")));
        grammar.put("T", Arrays.asList(Arrays.asList("F", "T'")));
        grammar.put("T'", Arrays.asList(Arrays.asList("*", "F", "T'"), Arrays.asList("ε")));
        grammar.put("F", Arrays.asList(Arrays.asList("(", "E", ")"), Arrays.asList("id")));

        FirstCalculator firstCalc = new FirstCalculator(grammar);
        Map<String, Set<String>> firstSets = firstCalc.computeFirstSets();

        for (String symbol : firstSets.keySet()) {
            System.out.println("FIRST(" + symbol + ") = " + firstSets.get(symbol));
        }
    }
}
