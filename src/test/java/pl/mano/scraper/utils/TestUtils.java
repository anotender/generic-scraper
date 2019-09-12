package pl.mano.scraper.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class TestUtils {

    public static String getResourceAsString(String resource) {
        try {
            return IOUtils.toString(
                    TestUtils.class.getResourceAsStream(resource),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
