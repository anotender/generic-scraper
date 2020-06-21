package pl.mano.scraper.extractor.parser;

import java.math.BigDecimal;

public class StringToBigDecimalParser extends StringToNumberParser<BigDecimal> {

    public StringToBigDecimalParser() {
        super(BigDecimal::new);
    }

}
