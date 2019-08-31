package pl.mano.scraper.model;

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
public class CollectionOfPrimitiveParametersDTO {

    @XPath("/div[@id=\"paragraphs\"]/p")
    private List<String> collectionOfStringValues;

    @XPath("/div[@id=\"numbers\"]/p")
    private List<Integer> collectionOfIntegerValues;

    @XPath("/div[@id=\"paragraphs\"]/wrong")
    private List<String> emptyCollectionForWrongXpath;

}
