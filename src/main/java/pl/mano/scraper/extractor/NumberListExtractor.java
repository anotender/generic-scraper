package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import pl.mano.scraper.extractor.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;

public final class NumberListExtractor<N> implements Extractor<List<N>> {

    private final Extractor<List<String>> stringListExtractor;

    private final Parser<String, N> stringToNumberParser;

    NumberListExtractor(Extractor<List<String>> stringListExtractor, Parser<String, N> stringToNumberParser) {
        this.stringListExtractor = stringListExtractor;
        this.stringToNumberParser = stringToNumberParser;
    }

    @Override
    public List<N> apply(TagNode tagNode, String xPath) {
        return stringListExtractor
                .apply(tagNode, xPath)
                .stream()
                .map(stringToNumberParser)
                .collect(Collectors.toList());
    }
}
