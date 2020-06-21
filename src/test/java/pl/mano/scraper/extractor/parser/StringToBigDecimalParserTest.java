package pl.mano.scraper.extractor.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringToBigDecimalParserTest {

    private final StringToBigDecimalParser stringToBigDecimalParser = new StringToBigDecimalParser();

    static Stream<Arguments> testCases() {
        return Stream.of(
                arguments("1", BigDecimal.ONE),
                arguments("1.0", new BigDecimal("1.0")),
                arguments("1 000", BigDecimal.valueOf(1_000L)),
                arguments("1000", BigDecimal.valueOf(1_000L)),
                arguments("1 000 000", BigDecimal.valueOf(1_000_000L)),
                arguments("1000000", BigDecimal.valueOf(1_000_000L)),
                arguments("-1", BigDecimal.ONE.negate()),
                arguments("0", BigDecimal.ZERO),
                arguments("    1    ", BigDecimal.ONE),
                arguments("no number", null),
                arguments("number between 1000 text", null),
                arguments(null, null),
                arguments("12.345", new BigDecimal("12.345")),
                arguments("-12.345", new BigDecimal("12.345").negate())
        );
    }

    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @MethodSource("testCases")
    void shouldParseStringValueToBigDecimal(String valueAsString, BigDecimal expectedOutput) {
        //when
        BigDecimal result = stringToBigDecimalParser.apply(valueAsString);

        //then
        then(result).isEqualTo(expectedOutput);
    }
}