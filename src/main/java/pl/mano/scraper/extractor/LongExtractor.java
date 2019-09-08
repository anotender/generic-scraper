package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import pl.mano.scraper.extractor.parser.Parser;

class LongExtractor implements Extractor<Long> {

    private final Extractor<String> stringExtractor;

    private final Parser<Long> longParser;

    LongExtractor(Extractor<String> stringExtractor, Parser<Long> longParser) {
        this.stringExtractor = stringExtractor;
        this.longParser = longParser;
    }

    @Override
    public Long apply(TagNode tagNode, String xPath) {
        return stringExtractor.andThen(longParser).apply(tagNode, xPath);
    }

}
