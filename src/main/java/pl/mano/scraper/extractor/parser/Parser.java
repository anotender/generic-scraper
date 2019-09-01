package pl.mano.scraper.extractor.parser;

import java.util.function.Function;

public interface Parser<T> extends Function<String, T> {
}
