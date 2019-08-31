package pl.mano.scraper.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mano.annotation.Scraped;
import pl.mano.annotation.XPath;

@Scraped(baseXPath = "/body")
@NoArgsConstructor
@Getter
@Setter
public class SinglePrimitiveParametersDTO {

    @XPath("/div[@id=\"paragraphs\"]/p[1]")
    private String singleStringValue;

    @XPath("/div[@id=\"numbers\"]/p[1]")
    private Integer singleIntegerValue;

    @XPath("/div/wrong")
    private String wrongXPathValue;

}
