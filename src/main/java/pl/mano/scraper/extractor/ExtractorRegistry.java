package pl.mano.scraper.extractor;

import pl.mano.scraper.extractor.parser.IntegerParser;
import pl.mano.scraper.extractor.parser.Parser;

import java.util.List;

public class ExtractorRegistry {

    private final StringExtractor stringExtractor;

    private final IntegerExtractor integerExtractor;

    private final StringListExtractor stringListExtractor;

    private final IntegerListExtractor integerListExtractor;

    public static ExtractorRegistry instance() {
        return new ExtractorRegistry();
    }

    private ExtractorRegistry() {
        Parser<Integer> integerParser = new IntegerParser();
        this.stringExtractor = new StringExtractor();
        this.integerExtractor = new IntegerExtractor(this.stringExtractor, integerParser);
        this.stringListExtractor = new StringListExtractor();
        this.integerListExtractor = new IntegerListExtractor(this.stringListExtractor, integerParser);
    }

    public Extractor<?> getNonCollectionExtractorForClass(Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return stringExtractor;
        } else if (clazz.equals(Integer.class)) {
            return integerExtractor;
        }
        return getDefaultNonCollectionExtractorForClass(clazz);
    }

    public Extractor<?> getCollectionExtractorForClass(Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return stringListExtractor;
        } else if (clazz.equals(Integer.class)) {
            return integerListExtractor;
        }
        return getDefaultCollectionExtractorForClass(clazz);
    }

    private Extractor<?> getDefaultNonCollectionExtractorForClass(Class<?> clazz) {
        return new NonCollectionExtractor(clazz, this);
    }

    private Extractor<List<?>> getDefaultCollectionExtractorForClass(Class<?> clazz) {
        return new CollectionExtractor(clazz, getDefaultNonCollectionExtractorForClass(clazz));
    }

}
