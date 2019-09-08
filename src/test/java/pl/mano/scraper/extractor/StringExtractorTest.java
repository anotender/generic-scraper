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

class StringExtractorTest {

    private static final String TEST_DOCUMENT = "" +
            "<html>" +
            "   <body>" +
            "       <div id=\"div1\">Value 1</div>" +
            "       <div id=\"div2\">Value 2</div>" +
            "       <div id=\"div3\">Value 3</div>" +
            "       <div id=\"div4\">Value 4</div>" +
            "   </body>" +
            "</html>";

    static Stream<Arguments> testCases() {
        return Stream.of(
                arguments("/body/div", null),
                arguments("/body/div[@id='div1']", "Value 1"),
                arguments("//*[@id='div1']", "Value 1"),
                arguments("//*[@id='div2']", "Value 2"),
                arguments("//*[@id='div2']/text()", "Value 2"),
                arguments("/no/match", null)
        );
    }

    private final StringExtractor stringExtractor = new StringExtractor();

    @ParameterizedTest(name = "XPath: \"{0}\" should be evaluated to \"{1}\"")
    @MethodSource("testCases")
    void shouldEvaluateXPathToGivenExpectedOutput(String xPath, String expectedOutput) {
        //given
        TagNode rootNode = new HtmlCleaner().clean(TEST_DOCUMENT);

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