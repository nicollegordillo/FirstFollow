import java.util.*;

public class FirstCalculator {
    private final Map<String, List<List<String>>> grammar;
    private final Map<String, Set<String>> firstSets;

    public FirstCalculator(Map<String, List<List<String>>> grammar) {
        this.grammar = grammar;
        this.firstSets = new HashMap<>();
    }

    public Map<String, Set<String>> computeFirstSets() {
        for (String nonTerminal : grammar.keySet()) {
            computeFirst(nonTerminal);
        }
        return firstSets;
    }

    private Set<String> computeFirst(String symbol) {
        if (firstSets.containsKey(symbol)) {
            return firstSets.get(symbol);
        }

        Set<String> first = new HashSet<>();
        firstSets.put(symbol, first);

        if (!grammar.containsKey(symbol)) {
            first.add(symbol); // Es un terminal
            return first;
        }

        for (List<String> production : grammar.get(symbol)) {
            for (int i = 0; i < production.size(); i++) {
                String prodSymbol = production.get(i);
                Set<String> prodFirst = computeFirst(prodSymbol);

                for (String f : prodFirst) {
                    if (!f.equals("ε")) {
                        first.add(f);
                    }
                }

                if (!prodFirst.contains("ε")) {
                    break;
                }

                if (i == production.size() - 1) {
                    first.add("ε");
                }
            }
        }

        return first;
    }
}
