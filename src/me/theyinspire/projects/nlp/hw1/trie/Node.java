package me.theyinspire.projects.nlp.hw1.trie;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Node {

    private final Map<String, Node> children;
    private int count;

    public Node() {
        children = new HashMap<>();
        count = 0;
    }

    public void increase() {
        count ++;
    }

    public void insert(String label) {
        children.putIfAbsent(label, new Node());
        children.get(label).increase();
    }

    public Node get(String label) {
        return children.getOrDefault(label, new Node());
    }

    public double probability(String label) {
        if (!children.containsKey(label)) {
            return 0D;
        }
        int count = get(label).count();
        int total = children.values().stream().mapToInt(Node::count).sum();
        return ((double) count) / ((double) total);
    }

    public Set<String> possibilities() {
        return children.keySet();
    }

    public int count() {
        return count;
    }

}
