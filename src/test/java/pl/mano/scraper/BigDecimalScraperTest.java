package pl.mano.scraper;

import org.junit.jupiter.api.Test;
import pl.mano.scraper.extractor.ExtractorRegistry;
import pl.mano.scraper.model.BigDecimalDTO;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.mano.scraper.utils.TestUtils.getResourceAsString;

public class BigDecimalScraperTest {

    private final String document = getResourceAsString("/bigDecimalTest.html");

    private final Scraper scraper = new Scraper(ExtractorRegistry.newInstance());

    @Test
    void shouldScrapBigDecimalSingleValue() {
        //when
        BigDecimalDTO bigDecimalDTO = scraper.scrapObject(document, BigDecimalDTO.class);

        //then
        then(bigDecimalDTO.getSingleBigDecimal()).isEqualByComparingTo(new BigDecimal("12.345"));
    }

    @Test
    void shouldScrapListOfBigDecimalValues() {
        //when
        BigDecimalDTO bigDecimalDTO = scraper.scrapObject(document, BigDecimalDTO.class);

        //then
        then(bigDecimalDTO.getListOfBigDecimals())
                .usingDefaultElementComparator()
                .containsExactly(
                        new BigDecimal("0"),
                        new BigDecimal("1.0"),
                        new BigDecimal("2"),
                        new BigDecimal("3"),
                        new BigDecimal("-4")
                );
    }
}
