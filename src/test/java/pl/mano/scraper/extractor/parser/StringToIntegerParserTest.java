package pl.mano.scraper.extractor.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringToIntegerParserTest {

    private final Parser<String, Integer> stringToIntegerParser = new StringToNumberParser<>(Integer::valueOf);

    static Stream<Arguments> testCases() {
        return Stream.of(
                arguments("0", 0),
                arguments("1", 1),
                arguments("-1", -1),
                arguments(null, null),
                arguments("2 147 483 647", Integer.MAX_VALUE),
                arguments("2 147 483 648", null),
                arguments("-2 147 483 648", Integer.MIN_VALUE),
                arguments("-2 147 483 649", null)
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("testCases")
    void shouldParseLongValueToIntegerValue(String stringValue, Integer expectedValue) {
        //when
        Integer result = stringToIntegerParser.apply(stringValue);

        //then
        then(result).isEqualTo(expectedValue);
    }
}