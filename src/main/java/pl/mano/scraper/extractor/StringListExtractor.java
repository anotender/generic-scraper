package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import pl.mano.scraper.extractor.parser.Parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class StringListExtractor implements Extractor<List<String>> {

    private final Parser<Object, String> objectToStringParser;

    StringListExtractor(Parser<Object, String> objectToStringParser) {
        this.objectToStringParser = objectToStringParser;
    }

    @Override
    public List<String> apply(TagNode tagNode, String xPath) {
        try {
            return Arrays.stream(tagNode.evaluateXPath(xPath))
                    .map(objectToStringParser)
                    .collect(Collectors.toList());
        } catch (XPatherException e) {
            return Collections.emptyList();
        }
    }

}
