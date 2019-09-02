package pl.mano.scraper.model.nested;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mano.annotation.XPath;

@NoArgsConstructor
@Getter
@Setter
public class CollectionOfNestedDTO {

    @XPath("/p")
    private String stringValue;

    @XPath("/div")
    private Integer integerValue;

}