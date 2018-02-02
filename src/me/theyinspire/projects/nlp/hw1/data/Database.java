package me.theyinspire.projects.nlp.hw1.data;

public class Database {

    private final DataSet positive;
    private final DataSet negative;
    private final DataSet aggregate;

    public Database() {
        aggregate = new DataSet("All Reviews");
        positive = new DataSet("Positive Reviews");
        negative = new DataSet("Negative Reviews");
    }

    public DataSet dataSet(DataSetType type) {
        switch (type) {
            case POSITIVE:
                return positive;
            case NEGATIVE:
                return negative;
            case AGGREGATE:
                return aggregate;
        }
        return null;
    }

}
