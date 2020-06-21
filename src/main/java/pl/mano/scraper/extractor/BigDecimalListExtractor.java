package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import pl.mano.scraper.extractor.parser.Parser;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

class BigDecimalListExtractor implements Extractor<List<BigDecimal>> {

    private final Extractor<List<String>> stringListExtractor;

    private final Parser<String, BigDecimal> bigDecimalToLongParser;

    BigDecimalListExtractor(Extractor<List<String>> stringListExtractor, Parser<String, BigDecimal> stringToBigDecimalParser) {
        this.stringListExtractor = stringListExtractor;
        this.bigDecimalToLongParser = stringToBigDecimalParser;
    }

    @Override
    public List<BigDecimal> apply(TagNode tagNode, String xPath) {
        return stringListExtractor
                .apply(tagNode, xPath)
                .stream()
                .map(bigDecimalToLongParser)
                .collect(Collectors.toList());
    }

}
