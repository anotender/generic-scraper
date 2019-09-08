package pl.mano.scraper.extractor;

import pl.mano.scraper.extractor.parser.IntegerParser;
import pl.mano.scraper.extractor.parser.Parser;

import java.util.List;
import java.util.Map;

public class ExtractorRegistry {

    private final Map<Class<?>, Extractor<?>> nonCollectionExtractors;

    private final Map<Class<?>, Extractor<?>> collectionExtractors;

    public static ExtractorRegistry instance() {
        return new ExtractorRegistry();
    }

    private ExtractorRegistry() {
        this.nonCollectionExtractors = initNonCollectionExtractors();
        this.collectionExtractors = initCollectionExtractors();
    }

    public Extractor<?> getNonCollectionExtractorForClass(Class<?> clazz) {
        return nonCollectionExtractors.getOrDefault(clazz, getDefaultNonCollectionExtractorForClass(clazz));
    }

    public Extractor<?> getCollectionExtractorForClass(Class<?> clazz) {
        return collectionExtractors.getOrDefault(clazz, getDefaultCollectionExtractorForClass(clazz));
    }

    private Map<Class<?>, Extractor<?>> initNonCollectionExtractors() {
        Parser<Integer> integerParser = new IntegerParser();
        StringExtractor stringExtractor = new StringExtractor();
        IntegerExtractor integerExtractor = new IntegerExtractor(stringExtractor, integerParser);
        return Map.of(
                String.class, stringExtractor,
                Integer.class, integerExtractor
        );
    }

    private Map<Class<?>, Extractor<?>> initCollectionExtractors() {
        Parser<Integer> integerParser = new IntegerParser();
        StringListExtractor stringListExtractor = new StringListExtractor();
        IntegerListExtractor integerListExtractor = new IntegerListExtractor(stringListExtractor, integerParser);
        return Map.of(
                String.class, stringListExtractor,
                Integer.class, integerListExtractor
        );
    }

    private Extractor<?> getDefaultNonCollectionExtractorForClass(Class<?> clazz) {
        return new NonCollectionExtractor(clazz, this);
    }

    private Extractor<List<?>> getDefaultCollectionExtractorForClass(Class<?> clazz) {
        return new CollectionExtractor(getDefaultNonCollectionExtractorForClass(clazz));
    }

}
