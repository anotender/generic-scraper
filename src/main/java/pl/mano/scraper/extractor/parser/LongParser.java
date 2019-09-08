package pl.mano.scraper.extractor.parser;

public class LongParser implements Parser<Long> {

    @Override
    public Long apply(String stringValue) {
        if (stringValue == null) {
            return null;
        }

        stringValue = stringValue
                .trim()
                .replaceAll("\\s+", "");

        return parseStringValue(stringValue);
    }

    private Long parseStringValue(String stringValue) {
        try {
            return Long.parseLong(stringValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
