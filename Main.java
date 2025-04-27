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

        System.out.println("Conjuntos FIRST de los no terminales:");
        for (String symbol : grammar.keySet()) {
            System.out.println("FIRST(" + symbol + ") = " + firstSets.get(symbol));
        }

        FollowCalculator followCalc = new FollowCalculator(grammar, firstSets);
        Map<String, Set<String>> followSets = followCalc.computeFollowSets("E"); // "E" es el símbolo inicial

        System.out.println("\nConjuntos FOLLOW de los no terminales:");
        for (String symbol : grammar.keySet()) {
            System.out.println("FOLLOW(" + symbol + ") = " + followSets.get(symbol));
        }
    }
}
