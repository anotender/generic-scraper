package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;

class IntegerExtractorTest {

    @InjectMocks
    private IntegerExtractor integerExtractor;

    @Mock
    private LongExtractor longExtractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

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
    void shouldParseIntegerFromLongValue(Long longValue, Integer expectedValue) {
        //given
        TagNode anyTagNode = new TagNode("AnyTagNode");
        String anyXPath = "any/x/path";
        given(longExtractor.apply(anyTagNode, anyXPath)).willReturn(longValue);

        //when
        Integer result = integerExtractor.apply(anyTagNode, anyXPath);

        //then
        then(result).isEqualTo(expectedValue);
    }
}