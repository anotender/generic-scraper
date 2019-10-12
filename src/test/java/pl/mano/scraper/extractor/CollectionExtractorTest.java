package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.util.Arrays.array;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CollectionExtractorTest {

    @InjectMocks
    private CollectionExtractor collectionExtractor;

    @Mock
    private Extractor<Object> nonCollectionExtractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldReturnListOfExtractedObjects() throws XPatherException {
        //given
        TagNode tagNode = mock(TagNode.class);
        String anyXPath = "any/x/path";
        TagNode[] tagNodes = array(setUpTagNode(1), setUpTagNode(2), setUpTagNode(3));
        given(tagNode.evaluateXPath(anyXPath)).willReturn(tagNodes);

        //when
        List<Object> result = collectionExtractor.apply(tagNode, anyXPath);

        //then
        then(result)
                .isNotNull()
                .isNotEmpty()
                .containsExactly(1, 2, 3);
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoResultsForGivenXPath() throws XPatherException {
        //given
        TagNode tagNode = mock(TagNode.class);
        String anyXPath = "any/x/path";
        given(tagNode.evaluateXPath(anyXPath)).willReturn(array());

        //when
        List<Object> result = collectionExtractor.apply(tagNode, anyXPath);

        //then
        then(result).isNotNull().isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenXPatherExceptionIsThrown() throws XPatherException {
        //given
        TagNode tagNode = mock(TagNode.class);
        String anyXPath = "any/x/path";
        given(tagNode.evaluateXPath(anyXPath)).willThrow(XPatherException.class);

        //when
        List<Object> result = collectionExtractor.apply(tagNode, anyXPath);

        //then
        then(result).isNotNull().isEmpty();
    }

    private TagNode setUpTagNode(Object extractedValue) {
        TagNode tagNode = new TagNode("any");
        given(nonCollectionExtractor.apply(tagNode, "")).willReturn(extractedValue);
        return tagNode;
    }
}