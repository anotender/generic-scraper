package pl.mano.scraper.model.nested;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mano.annotation.Scraped;
import pl.mano.annotation.XPath;

import java.util.List;

@Scraped(baseXPath = "/body")
@NoArgsConstructor
@Getter
@Setter
public class RootDTO {

    @XPath("/div[@id=\"paragraphs\"]/p")
    private List<String> paragraphs;

    @XPath("/div[@id=\"numbers\"]")
    private SingleNestedDTO singleNestedDTO;

    @XPath("/div/div[@class='some-class']")
    private List<CollectionOfNestedDTO> nestedDTOs;

}
