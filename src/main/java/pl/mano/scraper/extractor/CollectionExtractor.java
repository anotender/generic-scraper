package pl.mano.scraper.extractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
class CollectionExtractor implements Extractor<List<?>> {

    private final Extractor<?> nonCollectionExtractor;

    @Override
    public List<Object> apply(TagNode rootNode, String rootXPath) {
        try {
            log.debug("Scraping root xPath [{}]", rootXPath);
            return Arrays.stream(rootNode.evaluateXPath(rootXPath))
                    .map(TagNode.class::cast)
                    .map(node -> nonCollectionExtractor.apply(node, ""))
                    .collect(Collectors.toList());
        } catch (XPatherException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }

    }

}
