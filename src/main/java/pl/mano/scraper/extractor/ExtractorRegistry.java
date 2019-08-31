package pl.mano.scraper.extractor;

import java.util.Optional;

public class ExtractorRegistry {

    private final StringExtractor stringExtractor;

    private final IntegerExtractor integerExtractor;

    private final StringListExtractor stringListExtractor;

    private final IntegerListExtractor integerListExtractor;

    public static ExtractorRegistry instance() {
        return new ExtractorRegistry();
    }

    private ExtractorRegistry() {
        this.stringExtractor = new StringExtractor();
        this.integerExtractor = new IntegerExtractor(this.stringExtractor);
        this.stringListExtractor = new StringListExtractor();
        this.integerListExtractor = new IntegerListExtractor(this.stringListExtractor);
    }

    public Optional<Extractor<?>> getNonCollectionExtractorForClass(Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return Optional.of(stringExtractor);
        } else if (clazz.equals(Integer.class)) {
            return Optional.of(integerExtractor);
        }
        return Optional.empty();
    }

    public Optional<Extractor<?>> getCollectionExtractorForClass(Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return Optional.of(stringListExtractor);
        } else if (clazz.equals(Integer.class)) {
            return Optional.of(integerListExtractor);
        }
        return Optional.empty();
    }

}
