package pl.mano.scraper.extractor;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.mano.scraper.extractor.parser.ObjectToStringParser;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static pl.mano.scraper.utils.TestUtils.getResourceAsString;

class StringListExtractorTest {

    private final String document = getResourceAsString("/test.html");

    private final StringListExtractor stringListExtractor = new StringListExtractor(new ObjectToStringParser());

    static Stream<Arguments> testCases() {
        return Stream.of(
                arguments("/body/div[@id='paragraphs']/p", asList("Paragraph 1", "Paragraph 2", "Paragraph 3", "Paragraph 4")),
                arguments("/body/div[@id='paragraphs']/p/text()", asList("Paragraph 1", "Paragraph 2", "Paragraph 3", "Paragraph 4")),
                arguments("/body/div[@id='links']/a/@href", asList("href1", "href2", "href3", "href4")),
                arguments("/no/match", emptyList())
        );
    }

    @ParameterizedTest(name = "XPath: \"{0}\" should be evaluated to \"{1}\"")
    @MethodSource("testCases")
    void shouldEvaluateXPathToGivenExpectedOutput(String xPath, List<String> expectedOutput) {
        //given
        TagNode rootNode = new HtmlCleaner().clean(document);

        //when
        List<String> result = stringListExtractor.apply(rootNode, xPath);

        //then
        then(result)
                .isNotNull()
                .hasSameSizeAs(expectedOutput)
                .containsExactlyElementsOf(expectedOutput);
    }

    @Test
    void shouldReturnEmptyListWhenTheXPatherExceptionIsThrown() throws XPatherException {
        //given
        TagNode tagNode = mock(TagNode.class);
        String anyXPath = "any/x/path";
        given(tagNode.evaluateXPath(anyXPath)).willThrow(XPatherException.class);

        //when
        List<String> result = stringListExtractor.apply(tagNode, anyXPath);

        //then
        then(result).isNotNull().isEmpty();
    }

}