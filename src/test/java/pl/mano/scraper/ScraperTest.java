package pl.mano.scraper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

abstract class ScraperTest {

    protected String getResourceAsString(String resource) {
        try {
            return IOUtils.toString(
                    SinglePrimitiveParametersScraperTest.class.getResourceAsStream(resource),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
