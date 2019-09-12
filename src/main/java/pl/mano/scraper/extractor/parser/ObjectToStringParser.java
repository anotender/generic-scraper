package pl.mano.scraper.extractor.parser;

import org.htmlcleaner.TagNode;

public class ObjectToStringParser implements Parser<Object, String> {

    @Override
    public String apply(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof TagNode) {
            return extractTagNodeText((TagNode) object);
        }
        return object.toString();
    }

    private String extractTagNodeText(TagNode node) {
        return node.getText().toString();
    }

}
