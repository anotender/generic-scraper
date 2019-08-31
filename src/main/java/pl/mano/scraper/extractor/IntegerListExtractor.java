package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class IntegerListExtractor implements Extractor<List<Integer>> {

    private final Extractor<List<String>> stringListExtractor;

    IntegerListExtractor(Extractor<List<String>> stringListExtractor) {
        this.stringListExtractor = stringListExtractor;
    }

    @Override
    public List<Integer> apply(TagNode tagNode, String xPath) {
        return stringListExtractor
                .apply(tagNode, xPath)
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

}
