package pl.mano.scraper.extractor.parser;

public class IntegerParser implements Parser<Integer> {

    @Override
    public Integer apply(String stringValue) {
        if (stringValue == null) {
            return null;
        }

        stringValue = stringValue
                .trim()
                .replaceAll("\\s+", "");

        return parseStringValue(stringValue);
    }

    private Integer parseStringValue(String stringValue) {
        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
