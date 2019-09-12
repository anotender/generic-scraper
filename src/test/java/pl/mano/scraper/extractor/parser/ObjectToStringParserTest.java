package pl.mano.scraper.extractor.parser;

import org.htmlcleaner.ContentNode;
import org.htmlcleaner.TagNode;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.BDDAssertions.then;

class ObjectToStringParserTest {

    private final ObjectToStringParser parser = new ObjectToStringParser();

    @Test
    void shouldReturnNullIfPassedObjectIsNull() {
        //when
        String result = parser.apply(null);

        //then
        then(result).isNull();
    }

    @Test
    void shouldReturnTagNodeText() {
        //given
        TagNode tagNode = new TagNode("tag");
        tagNode.setChildren(singletonList(new ContentNode("content")));

        //when
        String result = parser.apply(tagNode);

        //then
        then(result).isNotNull().isEqualTo("content");
    }

    @Test
    void shouldReturnStringText() {
        //given
        String s = "content";

        //when
        String result = parser.apply(s);

        //then
        then(result).isNotNull().isEqualTo(s);
    }

    @Test
    void shouldReturnStringBuilderText() {
        //given
        StringBuilder sb = new StringBuilder().append("content");

        //when
        String result = parser.apply(sb);

        //then
        then(result).isNotNull().isEqualTo(sb.toString());
    }

}