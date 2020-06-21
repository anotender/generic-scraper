package pl.mano.scraper.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mano.annotation.Scraped;
import pl.mano.annotation.XPath;

import java.math.BigDecimal;
import java.util.List;

@Scraped(baseXPath = "/body")
@NoArgsConstructor
@Getter
@Setter
public class BigDecimalDTO {

    @XPath("/span[@id=\"bigDecimal\"]")
    private BigDecimal singleBigDecimal;

    @XPath("/div[@id=\"bigDecimals\"]/p")
    private List<BigDecimal> listOfBigDecimals;

}

