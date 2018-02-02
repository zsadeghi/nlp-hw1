package me.theyinspire.projects.nlp.hw1.ngram;

import me.theyinspire.projects.nlp.hw1.trie.Node;

import java.util.*;

public class NGramFinder {

    public Map<NGram, Double> find(Node root, int n) {
        return find(root, n, 1D, Collections.emptyList());
    }

    private Map<NGram, Double> find(Node root, int n, double probability, List<String> words) {
        final Map<NGram, Double> result = new HashMap<>();
        if (n == 0) {
            result.put(new NGram(words), probability);
            return result;
        }
        final Set<String> possibilities = root.possibilities();
        for (String possibility : possibilities) {
            final double candidateProbability = root.probability(possibility);
            final List<String> rest = new LinkedList<>(words);
            rest.add(possibility);
            final Node node = root.get(possibility);
            final Map<NGram, Double> found = find(node, n - 1, probability * candidateProbability, rest);
            result.putAll(found);
        }
        return result;
    }

}
