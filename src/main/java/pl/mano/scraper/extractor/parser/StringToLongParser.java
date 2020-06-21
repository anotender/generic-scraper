package pl.mano.scraper.extractor.parser;

public class StringToLongParser extends StringToNumberParser<Long> {

    public StringToLongParser() {
        super(Long::valueOf);
    }

}
