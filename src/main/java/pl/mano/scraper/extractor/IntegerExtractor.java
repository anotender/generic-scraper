package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import pl.mano.scraper.extractor.parser.Parser;

class IntegerExtractor implements Extractor<Integer> {

    private final Extractor<String> stringExtractor;

    private final Parser<Integer> integerParser;

    IntegerExtractor(Extractor<String> stringExtractor, Parser<Integer> integerParser) {
        this.stringExtractor = stringExtractor;
        this.integerParser = integerParser;
    }

    @Override
    public Integer apply(TagNode tagNode, String xPath) {
        return stringExtractor.andThen(integerParser).apply(tagNode, xPath);
    }

}
