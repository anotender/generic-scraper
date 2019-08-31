package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;

import java.util.function.BiFunction;

public interface Extractor<T> extends BiFunction<TagNode, String, T> {
}
