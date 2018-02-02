package me.theyinspire.projects.nlp.hw1;

import me.theyinspire.projects.nlp.hw1.data.DataSetType;
import me.theyinspire.projects.nlp.hw1.data.Database;
import me.theyinspire.projects.nlp.hw1.io.DataSetReader;
import me.theyinspire.projects.nlp.hw1.ngram.NGram;
import me.theyinspire.projects.nlp.hw1.ngram.NGramFinder;
import me.theyinspire.projects.nlp.hw1.trie.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        final File folder = new File(Main.class.getResource("/data").toURI());
        final Database database = new Database();
        final int limit = 3;
        System.out.println("Reading up to " + limit + "-grams from the datasets");
        final DataSetReader reader = new DataSetReader(limit);
        long totalWords = 0L;
        long positiveWords = 0L;
        long negativeWords = 0L;
        System.out.println(" + Reading negative reviews");
        negativeWords = reader.read(database, DataSetType.NEGATIVE, new File(folder, "Neg"));
        totalWords += negativeWords;
        System.out.println("   > Total words in negative reviews: " + negativeWords);
        System.out.println(" + Reading positive reviews");
        positiveWords = reader.read(database, DataSetType.POSITIVE, new File(folder, "Pos"));
        totalWords += positiveWords;
        System.out.println("   > Total words in positive reviews: " + positiveWords);
        // Question #1
        printTotalWords(totalWords);
        // Question #2
        printVocabularySize(database);
        // Question #3
        printTopTenNGrams(database);
        // Question #5
        printSampleProbabilities(database);
    }

    private static void printTotalWords(final long totalWords) {
        System.out.println(" + Total words read: " + totalWords);
    }

    private static void printVocabularySize(final Database database) {
        System.out.println(" + Unique words: " + database.dataSet(DataSetType.AGGREGATE).vocabulary().size());
    }

    private static void printSampleProbabilities(final Database database) {
        System.out.println("Sample 3-gram probabilities:");
        final Node root = database.dataSet(DataSetType.AGGREGATE).root();
        System.out.println(" + Pr{a | this, is} = " + root.get("this").get("is").probability("a"));
        System.out.println(" + Pr{a | one, of} = " + root.get("one").get("of").probability("a"));
        System.out.println(" + Pr{as | as, much} = " + root.get("as").get("much").probability("as"));
        System.out.println(" + Pr{it | for, what} = " + root.get("for").get("what").probability("it"));
        System.out.println(" + Pr{great | such, a} = " + root.get("such").get("a").probability("great"));
    }

    private static void printTopTenNGrams(final Database database) {
        System.out.println();
        final NGramFinder finder = new NGramFinder();
        System.out.println("Analyzing n-grams ...");
        for (DataSetType type : DataSetType.values()) {
            for (int i = 2; i <= 3; i++) {
                System.out.println(" + Top 10 " + i + "-grams for data set " + type + ":");
                final Node root = database.dataSet(type).root();
                final Map<NGram, Double> map = finder.find(root, i);
                final List<NGram> nGrams = new ArrayList<>(map.keySet());
                nGrams.sort(Comparator.comparingDouble(map::get).reversed());
                for (int j = 0; j < Math.min(10, nGrams.size()); j++) {
                    final NGram nGram = nGrams.get
                            (j);
                    System.out.println("    " + (j + 1) + ") " + nGram + "; Pr{...} = " + map.get(nGram) + "; "
                                               + "Frequency = " + nGram.frequency(root));
                }
            }
        }
    }

}
