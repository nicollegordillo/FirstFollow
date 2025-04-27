import java.util.*;

public class FollowCalculator {
    private final Map<String, List<List<String>>> grammar;
    private final Map<String, Set<String>> firstSets;
    private final Map<String, Set<String>> followSets;

    public FollowCalculator(Map<String, List<List<String>>> grammar, Map<String, Set<String>> firstSets) {
        this.grammar = grammar;
        this.firstSets = firstSets;
        this.followSets = new HashMap<>();
    }

    public Map<String, Set<String>> computeFollowSets(String startSymbol) {
        // Inicializar conjuntos vacíos
        for (String nonTerminal : grammar.keySet()) {
            followSets.put(nonTerminal, new HashSet<>());
        }
        // Regla 1: agregar $ al símbolo inicial
        followSets.get(startSymbol).add("$");

        boolean changed;
        do {
            changed = false;
            for (String head : grammar.keySet()) {
                for (List<String> production : grammar.get(head)) {
                    for (int i = 0; i < production.size(); i++) {
                        String B = production.get(i);
                        if (!grammar.containsKey(B)) {
                            continue; // B es terminal, no interesa
                        }

                        // Beta = lo que sigue de B
                        Set<String> firstOfBeta = new HashSet<>();
                        boolean betaCanBeEpsilon = true;
                        for (int j = i + 1; j < production.size(); j++) {
                            String symbolBeta = production.get(j);
                            Set<String> firstSymbolBeta = firstSets.getOrDefault(symbolBeta, new HashSet<>());

                            firstOfBeta.addAll(firstSymbolBeta);
                            if (!firstSymbolBeta.contains("ε")) {
                                betaCanBeEpsilon = false;
                                break;
                            }
                        }

                        // Regla 2: agregar FIRST(beta) sin epsilon
                        Set<String> followB = followSets.get(B);
                        int beforeSize = followB.size();
                        for (String terminal : firstOfBeta) {
                            if (!terminal.equals("ε")) {
                                followB.add(terminal);
                            }
                        }

                        // Regla 3: si beta puede ser ε o no existe beta
                        if (betaCanBeEpsilon || i == production.size() - 1) {
                            followB.addAll(followSets.get(head));
                        }

                        if (followB.size() > beforeSize) {
                            changed = true;
                        }
                    }
                }
            }
        } while (changed);

        return followSets;
    }
}
