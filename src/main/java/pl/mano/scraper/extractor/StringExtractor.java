package pl.mano.scraper.extractor;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.util.logging.Level;
import java.util.logging.Logger;

class StringExtractor implements Extractor<String> {

    private static final Logger LOGGER = Logger.getLogger(StringExtractor.class.getName());

    @Override
    public String apply(TagNode tagNode, String xPath) {
        try {
            Object[] results = tagNode.evaluateXPath(xPath);
            if (results.length > 1) {
                LOGGER.log(Level.WARNING, "More than one match for given XPath: " + xPath);
                return null;
            } else if (results.length == 0) {
                LOGGER.log(Level.WARNING, "No match for given XPath: " + xPath);
                return null;
            }
            Object result = results[0];
            if (result instanceof TagNode) {
                return extractTagNodeText((TagNode) result);
            }
            return result.toString();
        } catch (XPatherException e) {
            LOGGER.log(Level.WARNING, "Could not extract value for given XPath: " + xPath);
            return null;
        }
    }

    private String extractTagNodeText(TagNode node) {
        return node.getText().toString();
    }

}
