package pl.mano.scraper.extractor.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public final class StringToNumberParser<N> implements Parser<String, N> {

    private final Function<String, N> numberFromStringCreator;

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
            log.warn(e.getMessage(), e);
            return null;
        }
    }

}
