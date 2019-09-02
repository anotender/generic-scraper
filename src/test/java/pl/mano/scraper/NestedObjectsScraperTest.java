package pl.mano.scraper;

import org.junit.jupiter.api.Test;
import pl.mano.scraper.model.nested.RootDTO;
import pl.mano.scraper.model.nested.SingleNestedDTO;

import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.BDDAssertions.then;

public class NestedObjectsScraperTest extends ScraperTest {

    private final String document = getResourceAsString("/test.html");

    private final Scraper scraper = new Scraper();

    @Test
    void shouldScrapNotNestedProperty() {
        //when
        RootDTO parent = scraper.scrapObject(document, RootDTO.class);

        //then
        then(parent)
                .isNotNull()
                .extracting(RootDTO::getParagraphs).asList()
                .isNotEmpty()
                .containsOnly("Paragraph 1", "Paragraph 2", "Paragraph 3", "Paragraph 4");
    }

    @Test
    void shouldScrapSingleNestedProperty() {
        //when
        RootDTO parent = scraper.scrapObject(document, RootDTO.class);

        //then
        then(parent)
                .isNotNull()
                .extracting(RootDTO::getSingleNestedDTO)
                .isNotNull()
                .extracting(SingleNestedDTO::getFirstValue, SingleNestedDTO::getSecondValue, SingleNestedDTO::getThirdValue)
                .containsOnly(1, 2, "3");
    }

    @Test
    void shouldScrapCollectionOfNestedProperties() {
        //when
        RootDTO parent = scraper.scrapObject(document, RootDTO.class);

        //then
        then(parent)
                .isNotNull()
                .extracting(RootDTO::getNestedDTOs).asList()
                .isNotNull()
                .isNotEmpty()
                .extracting("stringValue", "integerValue")
                .containsOnly(
                        tuple("Div with some class 1", 1),
                        tuple("Div with some class 2", 2),
                        tuple("Div with some class 3", 3)
                );
    }
}
