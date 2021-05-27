package pl.mano.scraper.extractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.TagNode;
import pl.mano.scraper.extractor.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public final class NumberListExtractor<N> implements Extractor<List<N>> {

    private final Extractor<List<String>> stringListExtractor;
    private final Parser<String, N> stringToNumberParser;

    @Override
    public List<N> apply(TagNode tagNode, String xPath) {
        log.debug("Scraping xPath [{}]", xPath);
        return stringListExtractor
                .apply(tagNode, xPath)
                .stream()
                .map(stringToNumberParser)
                .collect(Collectors.toList());
    }
}
