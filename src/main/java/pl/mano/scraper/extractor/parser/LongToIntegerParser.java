package pl.mano.scraper.extractor.parser;

public class LongToIntegerParser implements Parser<Long, Integer> {

    @Override
    public Integer apply(Long longValue) {
        try {
            if (longValue == null) {
                return null;
            }
            return Math.toIntExact(longValue);
        } catch (ArithmeticException e) {
            return null;
        }
    }

}
