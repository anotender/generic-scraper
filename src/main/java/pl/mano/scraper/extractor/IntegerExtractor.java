package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;

class IntegerExtractor implements Extractor<Integer> {

    private final Extractor<String> stringExtractor;

    IntegerExtractor(Extractor<String> stringExtractor) {
        this.stringExtractor = stringExtractor;
    }

    @Override
    public Integer apply(TagNode tagNode, String xPath) {
        String stringValue = stringExtractor.apply(tagNode, xPath);

        if (stringValue == null) {
            return null;
        }

        stringValue = stringValue
                .trim()
                .replaceAll("\\s+", "");

        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
