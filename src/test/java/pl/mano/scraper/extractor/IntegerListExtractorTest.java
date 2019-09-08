package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import org.junit.jupiter.api.Test;
import pl.mano.scraper.extractor.parser.LongToIntegerParser;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IntegerListExtractorTest {

    private final LongListExtractor longListExtractorStub = mock(LongListExtractor.class);

    private final LongToIntegerParser longToIntegerParser = new LongToIntegerParser();

    private final IntegerListExtractor integerListExtractor = new IntegerListExtractor(longListExtractorStub, longToIntegerParser);

    @Test
    void shouldProperlyMapLongValuesToIntegerValues() {
        //given
        List<Long> longValues = Arrays.asList(
                0L,
                (long) Integer.MAX_VALUE,
                (long) Integer.MAX_VALUE + 1,
                (long) Integer.MIN_VALUE,
                (long) Integer.MIN_VALUE - 1,
                null
        );
        TagNode anyTagNode = new TagNode("AnyTagNode");
        String anyXPath = "/any/x/path";
        given(longListExtractorStub.apply(anyTagNode, anyXPath)).willReturn(longValues);

        //when
        List<Integer> integers = integerListExtractor.apply(anyTagNode, anyXPath);

        //then
        then(integers)
                .isNotNull()
                .hasSameSizeAs(longValues)
                .contains(0, Integer.MAX_VALUE, null, Integer.MIN_VALUE, null, null);
    }
}