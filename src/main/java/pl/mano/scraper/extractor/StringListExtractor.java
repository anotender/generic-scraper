package pl.mano.scraper.extractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import pl.mano.scraper.extractor.parser.Parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
class StringListExtractor implements Extractor<List<String>> {

    private final Parser<Object, String> objectToStringParser;

    @Override
    public List<String> apply(TagNode tagNode, String xPath) {
        try {
            log.debug("Scraping xPath [{}]", xPath);
            return Arrays.stream(tagNode.evaluateXPath(xPath))
                    .map(objectToStringParser)
                    .collect(Collectors.toList());
        } catch (XPatherException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

}
