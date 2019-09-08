package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;

class IntegerExtractor implements Extractor<Integer> {

    private final Extractor<Long> longExtractor;

    IntegerExtractor(Extractor<Long> longExtractor) {
        this.longExtractor = longExtractor;
    }

    @Override
    public Integer apply(TagNode tagNode, String xPath) {
        Long longValue = longExtractor.apply(tagNode, xPath);
        if (longValue == null) {
            return null;
        }
        return castLongToInteger(longValue);
    }

    private Integer castLongToInteger(Long longValue) {
        try {
            return Math.toIntExact(longValue);
        } catch (ArithmeticException e) {
            return null;
        }
    }

}
