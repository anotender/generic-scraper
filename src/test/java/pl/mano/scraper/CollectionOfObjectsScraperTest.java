package pl.mano.scraper;

import org.junit.jupiter.api.Test;
import pl.mano.scraper.model.CollectionOfObjectsDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.BDDAssertions.then;

class CollectionOfObjectsScraperTest extends ScraperTest {

    private final String document = getResourceAsString("/test.html");

    private final Scraper scraper = new Scraper();

    @Test
    void shouldScrapCollectionOfObjects() {
        //when
        List<CollectionOfObjectsDTO> objects = scraper.scrapObjects(document, CollectionOfObjectsDTO.class);

        //then
        then(objects)
                .isNotNull()
                .isNotEmpty()
                .extracting(CollectionOfObjectsDTO::getStringValue, CollectionOfObjectsDTO::getIntegerValue)
                .containsExactly(
                        tuple("Div with some class 1", 1),
                        tuple("Div with some class 2", 2),
                        tuple("Div with some class 3", 3)
                );
    }

}