package me.theyinspire.projects.nlp.hw1.data;

import me.theyinspire.projects.nlp.hw1.trie.Node;

import java.util.HashMap;
import java.util.Map;

public class DataSet {

    private final String name;
    private final Map<String, Long> vocabulary;
    private final Node root;

    public DataSet(final String name) {
        this.name = name;
        vocabulary = new HashMap<>();
        root = new Node();
    }

    public Map<String, Long> vocabulary() {
        return vocabulary;
    }

    public Node root() {
        return root;
    }

}
