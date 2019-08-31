package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class StringListExtractor implements Extractor<List<String>> {

    @Override
    public List<String> apply(TagNode tagNode, String xPath) {
        try {
            return Arrays.stream(tagNode.evaluateXPath(xPath))
                    .map(TagNode.class::cast)
                    .map(TagNode::getText)
                    .map(CharSequence::toString)
                    .collect(Collectors.toList());
        } catch (XPatherException e) {
            return Collections.emptyList();
        }
    }

}
