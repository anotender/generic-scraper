package pl.mano.scraper;

import org.junit.jupiter.api.Test;
import pl.mano.scraper.model.SinglePrimitiveParametersDTO;

import static org.assertj.core.api.BDDAssertions.then;

class SinglePrimitiveParametersScraperTest extends ScraperTest {

    private final String document = getResourceAsString("/test.html");

    private final Scraper scraper = new Scraper();

    @Test
    void shouldScrapSingleStringValue() {
        //when
        SinglePrimitiveParametersDTO singlePrimitiveParametersDTO = scraper.scrapSingleObject(document, SinglePrimitiveParametersDTO.class);

        //then
        then(singlePrimitiveParametersDTO)
                .isNotNull()
                .extracting(SinglePrimitiveParametersDTO::getSingleStringValue)
                .isEqualTo("Paragraph 1");
    }

    @Test
    void shouldScrapSingleIntegerValue() {
        //when
        SinglePrimitiveParametersDTO singlePrimitiveParametersDTO = scraper.scrapSingleObject(document, SinglePrimitiveParametersDTO.class);

        //then
        then(singlePrimitiveParametersDTO)
                .isNotNull()
                .extracting(SinglePrimitiveParametersDTO::getSingleIntegerValue)
                .isEqualTo(1);
    }

    @Test
    void shouldScrapNullWhenTheXPathIsWrong() {
        //when
        SinglePrimitiveParametersDTO singlePrimitiveParametersDTO = scraper.scrapSingleObject(document, SinglePrimitiveParametersDTO.class);

        //then
        then(singlePrimitiveParametersDTO)
                .isNotNull()
                .extracting(SinglePrimitiveParametersDTO::getWrongXPathValue)
                .isNull();
    }

}