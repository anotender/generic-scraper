package pl.mano.scraper.model.nested;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mano.annotation.XPath;

@NoArgsConstructor
@Getter
@Setter
public class SingleNestedDTO {

    @XPath("/p[1]")
    private Integer firstValue;

    @XPath("/p[2]")
    private Long secondValue;

    @XPath("/p[3]")
    private String thirdValue;

}
