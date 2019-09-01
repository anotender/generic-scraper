package pl.mano.scraper.extractor.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class IntegerParserTest {

    private final IntegerParser integerParser = new IntegerParser();

    static Stream<Arguments> testCases() {
        return Stream.of(
                arguments("1", 1),
                arguments("1 000", 1_000),
                arguments("1000", 1_000),
                arguments("1 000 000", 1_000_000),
                arguments("1000000", 1_000_000),
                arguments("-1", -1),
                arguments("0", 0),
                arguments("    1    ", 1),
                arguments("no number", null),
                arguments("number between 1000 text", null),
                arguments(null, null),
                arguments("2 147 483 647", 2_147_483_647),
                arguments("2 147 483 648", null),
                arguments("-2 147 483 648", -2_147_483_648),
                arguments("-2 147 483 649", null),
                arguments("123456789", 123456789)
        );
    }

    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @MethodSource("testCases")
    void shouldReturnExpectedOutputForGivenInput(String valueAsString, Integer expectedOutput) {
        //when
        Integer result = integerParser.apply(valueAsString);

        //then
        then(result).isEqualTo(expectedOutput);
    }
}