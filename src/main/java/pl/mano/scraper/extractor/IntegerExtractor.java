package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import pl.mano.scraper.extractor.parser.Parser;

class IntegerExtractor implements Extractor<Integer> {

    private final Extractor<Long> longExtractor;

    private final Parser<Long, Integer> longToIntegerParser;

    IntegerExtractor(Extractor<Long> longExtractor, Parser<Long, Integer> longToIntegerParser) {
        this.longExtractor = longExtractor;
        this.longToIntegerParser = longToIntegerParser;
    }

    @Override
    public Integer apply(TagNode tagNode, String xPath) {
        return longExtractor.andThen(longToIntegerParser).apply(tagNode, xPath);
    }

}
