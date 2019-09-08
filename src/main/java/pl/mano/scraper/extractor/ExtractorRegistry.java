package pl.mano.scraper.extractor;

import pl.mano.scraper.extractor.parser.LongToIntegerParser;
import pl.mano.scraper.extractor.parser.Parser;
import pl.mano.scraper.extractor.parser.StringToLongParser;

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
        Parser<String, Long> stringToLongParser = new StringToLongParser();
        Parser<Long, Integer> longToIntegerParser = new LongToIntegerParser();
        Extractor<String> stringExtractor = new StringExtractor();
        Extractor<Long> longExtractor = stringExtractor.andThen(stringToLongParser);
        Extractor<Integer> integerExtractor = longExtractor.andThen(longToIntegerParser);
        return Map.of(
                String.class, stringExtractor,
                Integer.class, integerExtractor,
                Long.class, longExtractor
        );
    }

    private Map<Class<?>, Extractor<?>> initCollectionExtractors() {
        Parser<String, Long> stringToLongParser = new StringToLongParser();
        Parser<Long, Integer> longToIntegerParser = new LongToIntegerParser();
        Extractor<List<String>> stringListExtractor = new StringListExtractor();
        Extractor<List<Long>> longListExtractor = new LongListExtractor(stringListExtractor, stringToLongParser);
        Extractor<List<Integer>> integerListExtractor = new IntegerListExtractor(longListExtractor, longToIntegerParser);
        return Map.of(
                String.class, stringListExtractor,
                Integer.class, integerListExtractor,
                Long.class, longListExtractor
        );
    }

    private Extractor<?> getDefaultNonCollectionExtractorForClass(Class<?> clazz) {
        return new NonCollectionExtractor(clazz, this);
    }

    private Extractor<List<?>> getDefaultCollectionExtractorForClass(Class<?> clazz) {
        return new CollectionExtractor(getDefaultNonCollectionExtractorForClass(clazz));
    }

}
