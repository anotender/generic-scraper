package pl.mano.scraper.extractor;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static pl.mano.scraper.utils.TestUtils.getResourceAsString;

class StringExtractorTest {

    private final String document = getResourceAsString("/test.html");

    static Stream<Arguments> testCases() {
        return Stream.of(
                arguments("/body/div", null),
                arguments("/body/div[@id='singleDivWithId']", "Value"),
                arguments("//*[@href='href1']", "Href 1"),
                arguments("//*[@href='href2']", "Href 2"),
                arguments("//*[@href='href2']/text()", "Href 2"),
                arguments("/no/match", null)
        );
    }

    private final StringExtractor stringExtractor = new StringExtractor();

    @ParameterizedTest(name = "XPath: \"{0}\" should be evaluated to \"{1}\"")
    @MethodSource("testCases")
    void shouldEvaluateXPathToGivenExpectedOutput(String xPath, String expectedOutput) {
        //given
        TagNode rootNode = new HtmlCleaner().clean(document);

        //when
        String result = stringExtractor.apply(rootNode, xPath);

        //then
        then(result).isEqualTo(expectedOutput);
    }

    @Test
    void shouldReturnNullWhenTheXPatherExceptionIsThrown() throws XPatherException {
        //given
        TagNode tagNode = mock(TagNode.class);
        String anyXPath = "any/x/path";
        given(tagNode.evaluateXPath(anyXPath)).willThrow(XPatherException.class);

        //when
        String result = stringExtractor.apply(tagNode, anyXPath);

        //then
        then(result).isNull();
    }
}