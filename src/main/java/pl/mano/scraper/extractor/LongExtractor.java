package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import pl.mano.scraper.extractor.parser.Parser;

class LongExtractor implements Extractor<Long> {

    private final Extractor<String> stringExtractor;

    private final Parser<String, Long> stringToLongParser;

    LongExtractor(Extractor<String> stringExtractor, Parser<String, Long> stringToLongParser) {
        this.stringExtractor = stringExtractor;
        this.stringToLongParser = stringToLongParser;
    }

    @Override
    public Long apply(TagNode tagNode, String xPath) {
        return stringExtractor.andThen(stringToLongParser).apply(tagNode, xPath);
    }

}
