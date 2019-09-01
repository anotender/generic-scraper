package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import pl.mano.scraper.extractor.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;

class IntegerListExtractor implements Extractor<List<Integer>> {

    private final Extractor<List<String>> stringListExtractor;

    private final Parser<Integer> integerParser;

    IntegerListExtractor(Extractor<List<String>> stringListExtractor, Parser<Integer> integerParser) {
        this.stringListExtractor = stringListExtractor;
        this.integerParser = integerParser;
    }

    @Override
    public List<Integer> apply(TagNode tagNode, String xPath) {
        return stringListExtractor
                .apply(tagNode, xPath)
                .stream()
                .map(integerParser)
                .collect(Collectors.toList());
    }

}
