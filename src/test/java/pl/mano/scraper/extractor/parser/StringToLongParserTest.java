package pl.mano.scraper.extractor.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringToLongParserTest {

    private final Parser<String, Long> stringToLongParser = new StringToNumberParser<>(Long::valueOf);

    static Stream<Arguments> testCases() {
        return Stream.of(
                arguments("1", 1L),
                arguments("1 000", 1_000L),
                arguments("1000", 1_000L),
                arguments("1 000 000", 1_000_000L),
                arguments("1000000", 1_000_000L),
                arguments("-1", -1L),
                arguments("0", 0L),
                arguments("    1    ", 1L),
                arguments("no number", null),
                arguments("number between 1000 text", null),
                arguments(null, null),
                arguments("9 223 372 036 854 775 807", Long.MAX_VALUE),
                arguments("9 223 372 036 854 775 808", null),
                arguments("-9 223 372 036 854 775 808", Long.MIN_VALUE),
                arguments("-9 223 372 036 854 775 809", null),
                arguments("123456789", 123456789L)
        );
    }

    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @MethodSource("testCases")
    void shouldParseStringValueToInteger(String valueAsString, Long expectedOutput) {
        //when
        Long result = stringToLongParser.apply(valueAsString);

        //then
        then(result).isEqualTo(expectedOutput);
    }
}