package me.theyinspire.projects.nlp.hw1.io;

import me.theyinspire.projects.nlp.hw1.trie.Node;
import me.theyinspire.projects.nlp.hw1.data.DataSet;
import me.theyinspire.projects.nlp.hw1.data.DataSetType;
import me.theyinspire.projects.nlp.hw1.data.Database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private final int limit;

    public FileReader(final int limit) {
        this.limit = limit;
    }

    public long read(Database db, File file, DataSetType... types) throws IOException {
        long result = 0L;
        try (
                final FileInputStream stream = new FileInputStream(file);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                ) {
            String line;
            final List<String> queue = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                line = line.trim();
                line = line.replaceAll("<.*?>", "");
                line = line.replaceAll("[,.;:'\"\\[\\]{}|\\\\?/<>~`!@#$%^&*()\\-_=+]", "");
                final String[] words = line.split("\\s+");
                for (String word : words) {
                    while (queue.size() >= limit) {
                        queue.remove(0);
                    }
                    queue.add(word);
                    for (DataSetType type : types) {
                        final DataSet dataSet = db.dataSet(type);
                        dataSet.vocabulary().putIfAbsent(word, 0L);
                        dataSet.vocabulary().put(word, dataSet.vocabulary().get(word) + 1);
                        result ++;
                        for (int i = queue.size() - 1; i >= 0; i --) {
                            Node context = dataSet.root();
                            for (int j = i; j < queue.size() - 1; j ++) {
                                context = context.get(queue.get(j));
                            }
                            context.insert(word);
                        }
                    }
                }
            }
        }
        return result;
    }

}
