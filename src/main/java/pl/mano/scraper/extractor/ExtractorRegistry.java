package pl.mano.scraper.extractor;

import pl.mano.scraper.extractor.parser.LongToIntegerParser;
import pl.mano.scraper.extractor.parser.ObjectToStringParser;
import pl.mano.scraper.extractor.parser.Parser;
import pl.mano.scraper.extractor.parser.StringToLongParser;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractorRegistry {

    private final Map<Class<?>, Extractor<?>> nonCollectionExtractors;

    private final Map<Class<?>, Extractor<?>> collectionExtractors;

    private ExtractorRegistry(Map<Class<?>, Extractor<?>> customNonCollectionExtractors) {
        var nonCollectionExtractors = new HashMap<>(initNonCollectionExtractors());
        nonCollectionExtractors.putAll(customNonCollectionExtractors);
        this.nonCollectionExtractors = Map.copyOf(nonCollectionExtractors);
        this.collectionExtractors = initCollectionExtractors();
    }

    public static ExtractorRegistry newInstance() {
        return newInstance(Collections.emptyMap());
    }

    public static ExtractorRegistry newInstance(Map<Class<?>, Extractor<?>> customNonCollectionExtractors) {
        return new ExtractorRegistry(customNonCollectionExtractors);
    }

    public Extractor<?> getNonCollectionExtractorForClass(Class<?> clazz) {
        return nonCollectionExtractors.getOrDefault(clazz, getDefaultNonCollectionExtractorForClass(clazz));
    }

    public Extractor<?> getCollectionExtractorForClass(Class<?> clazz) {
        return collectionExtractors.getOrDefault(clazz, getDefaultCollectionExtractorForClass(clazz));
    }

    private Map<Class<?>, Extractor<?>> initNonCollectionExtractors() {
        Parser<Object, String> objectToStringParser = new ObjectToStringParser();
        Parser<String, Long> stringToLongParser = new StringToLongParser();
        Parser<Long, Integer> longToIntegerParser = new LongToIntegerParser();
        Extractor<String> stringExtractor = new StringExtractor(objectToStringParser);
        Extractor<Long> longExtractor = stringExtractor.andThen(stringToLongParser);
        Extractor<Integer> integerExtractor = longExtractor.andThen(longToIntegerParser);
        return Map.of(
                String.class, stringExtractor,
                Integer.class, integerExtractor,
                Long.class, longExtractor
        );
    }

    private Map<Class<?>, Extractor<?>> initCollectionExtractors() {
        Parser<Object, String> objectToStringParser = new ObjectToStringParser();
        Parser<String, Long> stringToLongParser = new StringToLongParser();
        Parser<Long, Integer> longToIntegerParser = new LongToIntegerParser();
        Extractor<List<String>> stringListExtractor = new StringListExtractor(objectToStringParser);
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
