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
    private StringExtractor stringExtractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
                arguments("1", 1),
                arguments("1 000", 1_000),
                arguments("1 000 000", 1_000_000),
                arguments("-1", -1),
                arguments("0", 0),
                arguments("    1    ", 1),
                arguments("no number", null),
                arguments("number between 1000 text", null),
                arguments(null, null)
        );
    }

    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @MethodSource("testCases")
    void shouldReturnExpectedOutputForGivenInput(String valueAsString, Integer expectedOutput) {
        //given
        TagNode anyTagNode = new TagNode("AnyTagNode");
        String anyXPath = "/any/xpath/expression";
        given(stringExtractor.apply(anyTagNode, anyXPath)).willReturn(valueAsString);

        //when
        Integer result = integerExtractor.apply(anyTagNode, anyXPath);

        //then
        then(result).isEqualTo(expectedOutput);
    }
}