package pl.mano.scraper;

import org.junit.jupiter.api.Test;
import pl.mano.scraper.model.CollectionOfPrimitiveParametersDTO;
import pl.mano.scraper.model.SinglePrimitiveParametersDTO;

import static org.assertj.core.api.BDDAssertions.then;

class CollectionOfPrimitiveParametersScraperTest extends ScraperTest {

    private final String document = getResourceAsString("/test.html");

    private final Scraper scraper = new Scraper();

    @Test
    void shouldScrapCollectionOfStringValues() {
        //when
        CollectionOfPrimitiveParametersDTO collectionOfPrimitiveParametersDTO = scraper.scrapSingleObject(document, CollectionOfPrimitiveParametersDTO.class);

        //then
        then(collectionOfPrimitiveParametersDTO)
                .isNotNull()
                .extracting(CollectionOfPrimitiveParametersDTO::getCollectionOfStringValues)
                .isNotNull()
                .asList()
                .isNotEmpty()
                .containsExactly("Paragraph 1", "Paragraph 2", "Paragraph 3", "Paragraph 4");
    }

    @Test
    void shouldScrapCollectionOfIntegerValues() {
        //when
        CollectionOfPrimitiveParametersDTO collectionOfPrimitiveParametersDTO = scraper.scrapSingleObject(document, CollectionOfPrimitiveParametersDTO.class);

        //then
        then(collectionOfPrimitiveParametersDTO)
                .isNotNull()
                .extracting(CollectionOfPrimitiveParametersDTO::getCollectionOfIntegerValues)
                .isNotNull()
                .asList()
                .isNotEmpty()
                .containsExactly(1, 2, 3, 4);
    }
}