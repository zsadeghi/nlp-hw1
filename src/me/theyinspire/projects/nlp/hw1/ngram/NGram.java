package me.theyinspire.projects.nlp.hw1.ngram;

import me.theyinspire.projects.nlp.hw1.trie.Node;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NGram {

    private final List<String> words;

    public NGram(String... words) {
        this(Arrays.asList(words));
    }

    public NGram(final List<String> words) {
        this.words = words;
    }

    public int frequency(Node root) {
        Node context = root;
        for (int i = 0; i < words.size() - 1; i++) {
            String word = words.get(i);
            context = context.get(word);
        }
        return context.get(words.get(words.size() - 1)).count();
    }

    @Override
    public String toString() {
        return String.valueOf(words);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NGram nGram = (NGram) o;
        return Objects.equals(words, nGram.words);
    }

    @Override
    public int hashCode() {

        return Objects.hash(words);
    }
}
