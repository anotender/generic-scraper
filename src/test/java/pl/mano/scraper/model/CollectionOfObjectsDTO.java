package pl.mano.scraper.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mano.annotation.Scraped;
import pl.mano.annotation.XPath;

@Scraped(baseXPath = "//div[@class='some-class']")
@NoArgsConstructor
@Getter
@Setter
public class CollectionOfObjectsDTO {

    @XPath("/p")
    private String stringValue;

    @XPath("/div")
    private Integer integerValue;

}
