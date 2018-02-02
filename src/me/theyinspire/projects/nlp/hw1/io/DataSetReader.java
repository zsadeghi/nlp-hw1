package me.theyinspire.projects.nlp.hw1.io;

import me.theyinspire.projects.nlp.hw1.data.DataSetType;
import me.theyinspire.projects.nlp.hw1.data.Database;

import java.io.File;
import java.io.IOException;

public class DataSetReader {

    private final FileReader reader;

    public DataSetReader(final int limit) {
        reader = new FileReader(limit);
    }

    public long read(Database db, DataSetType type, File folder) throws IOException {
        long result = 0L;
        final File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.println("   + Reading " + file);
                final long words = reader.read(db, file, type, DataSetType.AGGREGATE);
                System.out.println("     > file contained " + words + " word(s).");
                result += words;
            }
        }
        return result;
    }

}
