package pl.mano.scraper;

import org.apache.commons.beanutils.BeanUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import pl.mano.annotation.Scraped;
import pl.mano.annotation.XPath;
import pl.mano.scraper.extractor.Extractor;
import pl.mano.scraper.extractor.ExtractorRegistry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Scraper {

    private final HtmlCleaner htmlCleaner = new HtmlCleaner();

    private final ExtractorRegistry extractorRegistry = ExtractorRegistry.instance();

    public <T> T scrapObject(String document, Class<T> clazz) {
        TagNode rootNode = htmlCleaner.clean(document);
        String rootXPath = clazz.getAnnotation(Scraped.class).baseXPath();
        return scrapObject(rootNode, rootXPath, clazz);
    }

    public <T> List<T> scrapObjects(String document, Class<T> clazz) {
        TagNode rootNode = htmlCleaner.clean(document);
        String rootXPath = clazz.getAnnotation(Scraped.class).baseXPath();
        return scrapObjects(rootNode, rootXPath, clazz);
    }

    private <T> T scrapObject(TagNode rootNode, String rootXPath, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(XPath.class))
                    .forEach(field -> {
                        String xPath = rootXPath + field.getAnnotation(XPath.class).value();
                        Extractor<?> extractor;
                        if (field.getType().equals(List.class)) {
                            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                            Class<?> listClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                            extractor = extractorRegistry
                                    .getCollectionExtractorForClass(listClass)
                                    .orElseGet(() -> (tagNode, x) -> scrapObjects(tagNode, x, listClass));
                        } else {
                            extractor = extractorRegistry
                                    .getNonCollectionExtractorForClass(field.getType())
                                    .orElseGet(() -> (tagNode, x) -> scrapObject(tagNode, x, field.getType()));
                        }
                        setProperty(instance, field.getName(), extractor.apply(rootNode, xPath));
                    });
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> List<T> scrapObjects(TagNode rootNode, String rootXPath, Class<T> clazz) {
        try {
            return Arrays.stream(rootNode.evaluateXPath(rootXPath))
                    .map(TagNode.class::cast)
                    .map(node -> scrapObject(node, "", clazz))
                    .collect(Collectors.toList());
        } catch (XPatherException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void setProperty(Object o, String propertyName, Object propertyValue) {
        try {
            BeanUtils.setProperty(o, propertyName, propertyValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
