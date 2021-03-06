package pl.mano.scraper;

import org.junit.jupiter.api.Test;
import pl.mano.scraper.extractor.ExtractorRegistry;
import pl.mano.scraper.model.CollectionOfPrimitiveParametersDTO;

import java.util.ArrayList;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.mano.scraper.utils.TestUtils.getResourceAsString;

class CollectionOfPrimitiveParametersScraperTest {

    private final String document = getResourceAsString("/test.html");

    private final Scraper scraper = new Scraper(ExtractorRegistry.newInstance());

    @Test
    void shouldScrapCollectionOfStringValues() {
        //when
        CollectionOfPrimitiveParametersDTO collectionOfPrimitiveParametersDTO = scraper.scrapObject(document, CollectionOfPrimitiveParametersDTO.class);

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
    void shouldScrapSpecificTypeCollectionOfStringValues() {
        //when
        CollectionOfPrimitiveParametersDTO collectionOfPrimitiveParametersDTO = scraper.scrapObject(document, CollectionOfPrimitiveParametersDTO.class);

        //then
        then(collectionOfPrimitiveParametersDTO)
                .isNotNull()
                .extracting(CollectionOfPrimitiveParametersDTO::getSpecificTypeCollectionOfStringValues)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .asList()
                .isNotEmpty()
                .containsExactly("Paragraph 1", "Paragraph 2", "Paragraph 3", "Paragraph 4");
    }

    @Test
    void shouldScrapCollectionOfIntegerValues() {
        //when
        CollectionOfPrimitiveParametersDTO collectionOfPrimitiveParametersDTO = scraper.scrapObject(document, CollectionOfPrimitiveParametersDTO.class);

        //then
        then(collectionOfPrimitiveParametersDTO)
                .isNotNull()
                .extracting(CollectionOfPrimitiveParametersDTO::getCollectionOfIntegerValues)
                .isNotNull()
                .asList()
                .isNotEmpty()
                .containsExactly(1, 2, 3, 4);
    }

    @Test
    void shouldScrapCollectionOfLongValues() {
        //when
        CollectionOfPrimitiveParametersDTO collectionOfPrimitiveParametersDTO = scraper.scrapObject(document, CollectionOfPrimitiveParametersDTO.class);

        //then
        then(collectionOfPrimitiveParametersDTO)
                .isNotNull()
                .extracting(CollectionOfPrimitiveParametersDTO::getCollectionOfLongValues)
                .isNotNull()
                .asList()
                .isNotEmpty()
                .containsExactly(1L, 2L, 3L, 4L);
    }
}