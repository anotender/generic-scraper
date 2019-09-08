package pl.mano.scraper.extractor.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LongToIntegerParserTest {

    private final LongToIntegerParser longToIntegerParser = new LongToIntegerParser();

    static Stream<Arguments> testCases() {
        return Stream.of(
                arguments(0L, 0),
                arguments(1L, 1),
                arguments(-1L, -1),
                arguments(null, null),
                arguments(2_147_483_647L, Integer.MAX_VALUE),
                arguments(2_147_483_648L, null),
                arguments(-2_147_483_648L, Integer.MIN_VALUE),
                arguments(-2_147_483_649L, null)
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("testCases")
    void shouldParseLongValueToIntegerValue(Long longValue, Integer expectedValue) {
        //when
        Integer result = longToIntegerParser.apply(longValue);

        //then
        then(result).isEqualTo(expectedValue);
    }
}