package pl.mano.scraper.extractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import pl.mano.scraper.extractor.parser.Parser;

@Slf4j
@RequiredArgsConstructor
class StringExtractor implements Extractor<String> {

    private final Parser<Object, String> objectToStringParser;

    @Override
    public String apply(TagNode tagNode, String xPath) {
        try {
            log.debug("Scraping xPath [{}]", xPath);
            Object[] results = tagNode.evaluateXPath(xPath);
            if (results.length > 1) {
                log.warn("More than one match {} for given xPath [{}]", results, xPath);
                return null;
            } else if (results.length == 0) {
                log.warn("No match for given xPath [{}]", xPath);
                return null;
            }
            return objectToStringParser.apply(results[0]);
        } catch (XPatherException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
