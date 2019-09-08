package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import pl.mano.scraper.extractor.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;

class IntegerListExtractor implements Extractor<List<Integer>> {

    private final Extractor<List<Long>> longListExtractor;

    private final Parser<Long, Integer> longToIntegerParser;

    IntegerListExtractor(Extractor<List<Long>> longListExtractor, Parser<Long, Integer> longToIntegerParser) {
        this.longListExtractor = longListExtractor;
        this.longToIntegerParser = longToIntegerParser;
    }

    @Override
    public List<Integer> apply(TagNode tagNode, String xPath) {
        return longListExtractor
                .apply(tagNode, xPath)
                .stream()
                .map(longToIntegerParser)
                .collect(Collectors.toList());
    }

}
