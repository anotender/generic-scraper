package pl.mano.scraper.extractor.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

public final class StringToNumberParser<N> implements Parser<String, N> {

    private final Function<String, N> numberFromStringCreator;

    public StringToNumberParser(Function<String, N> numberFromStringCreator) {
        this.numberFromStringCreator = numberFromStringCreator;
    }

    @Override
    public N apply(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        return parseStringValue(StringUtils.deleteWhitespace(stringValue));
    }

    private N parseStringValue(String stringValue) {
        try {
            return numberFromStringCreator.apply(stringValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
