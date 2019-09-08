package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Extractor<T> extends BiFunction<TagNode, String, T> {

    @Override
    default <V> Extractor<V> andThen(Function<? super T, ? extends V> after) {
        Objects.requireNonNull(after);
        return (tagNode, xPath) -> after.apply(apply(tagNode, xPath));
    }

}
