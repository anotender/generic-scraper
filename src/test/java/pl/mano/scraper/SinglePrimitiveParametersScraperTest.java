package pl.mano.scraper;

import org.junit.jupiter.api.Test;
import pl.mano.scraper.extractor.ExtractorRegistry;
import pl.mano.scraper.model.SinglePrimitiveParametersDTO;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.mano.scraper.utils.TestUtils.getResourceAsString;

class SinglePrimitiveParametersScraperTest {

    private final String document = getResourceAsString("/test.html");

    private final Scraper scraper = new Scraper(ExtractorRegistry.newInstance());

    @Test
    void shouldScrapSingleStringValue() {
        //when
        SinglePrimitiveParametersDTO singlePrimitiveParametersDTO = scraper.scrapObject(document, SinglePrimitiveParametersDTO.class);

        //then
        then(singlePrimitiveParametersDTO)
                .isNotNull()
                .extracting(SinglePrimitiveParametersDTO::getSingleStringValue)
                .isEqualTo("Paragraph 1");
    }

    @Test
    void shouldScrapSingleIntegerValue() {
        //when
        SinglePrimitiveParametersDTO singlePrimitiveParametersDTO = scraper.scrapObject(document, SinglePrimitiveParametersDTO.class);

        //then
        then(singlePrimitiveParametersDTO)
                .isNotNull()
                .extracting(SinglePrimitiveParametersDTO::getSingleIntegerValue)
                .isEqualTo(1);
    }

    @Test
    void shouldScrapSingleLongValue() {
        //when
        SinglePrimitiveParametersDTO singlePrimitiveParametersDTO = scraper.scrapObject(document, SinglePrimitiveParametersDTO.class);

        //then
        then(singlePrimitiveParametersDTO)
                .isNotNull()
                .extracting(SinglePrimitiveParametersDTO::getSingleLongValue)
                .isEqualTo(1L);
    }

    @Test
    void shouldScrapNullWhenTheXPathIsWrong() {
        //when
        SinglePrimitiveParametersDTO singlePrimitiveParametersDTO = scraper.scrapObject(document, SinglePrimitiveParametersDTO.class);

        //then
        then(singlePrimitiveParametersDTO)
                .isNotNull()
                .extracting(SinglePrimitiveParametersDTO::getWrongXPathValue)
                .isNull();
    }

}