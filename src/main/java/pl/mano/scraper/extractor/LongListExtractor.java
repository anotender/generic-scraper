package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import pl.mano.scraper.extractor.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;

class LongListExtractor implements Extractor<List<Long>> {

    private final Extractor<List<String>> stringListExtractor;

    private final Parser<String, Long> stringToLongParser;

    LongListExtractor(Extractor<List<String>> stringListExtractor, Parser<String, Long> stringToLongParser) {
        this.stringListExtractor = stringListExtractor;
        this.stringToLongParser = stringToLongParser;
    }

    @Override
    public List<Long> apply(TagNode tagNode, String xPath) {
        return stringListExtractor
                .apply(tagNode, xPath)
                .stream()
                .map(stringToLongParser)
                .collect(Collectors.toList());
    }

}
