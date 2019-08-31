package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;

class IntegerExtractor implements Extractor<Integer> {

    private final Extractor<String> stringExtractor;

    IntegerExtractor(Extractor<String> stringExtractor) {
        this.stringExtractor = stringExtractor;
    }

    @Override
    public Integer apply(TagNode tagNode, String xPath) {
        return Integer.parseInt(stringExtractor.apply(tagNode, xPath));
    }

}
