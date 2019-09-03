package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class CollectionExtractor implements Extractor<List<?>> {

    private final Class<?> clazz;

    private final Extractor<?> nonCollectionExtractor;

    CollectionExtractor(Class<?> clazz, Extractor<?> nonCollectionExtractor) {
        this.clazz = clazz;
        this.nonCollectionExtractor = nonCollectionExtractor;
    }

    @Override
    public List<Object> apply(TagNode rootNode, String rootXPath) {
        try {
            return Arrays.stream(rootNode.evaluateXPath(rootXPath))
                    .map(TagNode.class::cast)
                    .map(node -> nonCollectionExtractor.apply(node, ""))
                    .collect(Collectors.toList());
        } catch (XPatherException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

}
