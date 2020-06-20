package pl.mano.scraper;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import pl.mano.annotation.Scraped;
import pl.mano.scraper.extractor.ExtractorRegistry;

import java.util.List;

public final class Scraper {

    private final HtmlCleaner htmlCleaner;

    private final ExtractorRegistry extractorRegistry;

    public Scraper(ExtractorRegistry extractorRegistry) {
        this.htmlCleaner = new HtmlCleaner();
        this.extractorRegistry = extractorRegistry;
    }

    public <T> T scrapObject(String document, Class<T> clazz) {
        TagNode rootNode = htmlCleaner.clean(document);
        String rootXPath = clazz.getAnnotation(Scraped.class).baseXPath();
        return (T) extractorRegistry
                .getNonCollectionExtractorForClass(clazz)
                .apply(rootNode, rootXPath);
    }

    public <T> List<T> scrapObjects(String document, Class<T> clazz) {
        TagNode rootNode = htmlCleaner.clean(document);
        String rootXPath = clazz.getAnnotation(Scraped.class).baseXPath();
        return (List<T>) extractorRegistry
                .getCollectionExtractorForClass(clazz)
                .apply(rootNode, rootXPath);
    }
}
