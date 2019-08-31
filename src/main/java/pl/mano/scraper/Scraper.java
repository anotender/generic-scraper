package pl.mano.scraper;

import org.apache.commons.beanutils.BeanUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import pl.mano.annotation.Scraped;
import pl.mano.annotation.XPath;
import pl.mano.scraper.extractor.ExtractorRegistry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

public class Scraper {

    private final HtmlCleaner htmlCleaner = new HtmlCleaner();

    private final ExtractorRegistry extractorRegistry = ExtractorRegistry.instance();

    public <T> T scrapSingleObject(String document, Class<T> clazz) {
        try {
            TagNode rootNode = htmlCleaner.clean(document);
            String rootXPath = clazz.getAnnotation(Scraped.class).baseXPath();
            return scrapSingleObject(rootNode, rootXPath, clazz);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T scrapSingleObject(TagNode rootNode, String rootXPath, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T instance = clazz.getDeclaredConstructor().newInstance();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(XPath.class))
                .forEach(field -> {
                    String xPath = rootXPath + field.getAnnotation(XPath.class).value();
                    Object result = null;
                    if (field.getType().equals(List.class)) {
                        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                        Class<?> listClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                        result = extractorRegistry
                                .getCollectionExtractorForClass(listClass)
                                .orElseThrow(RuntimeException::new)
                                .apply(rootNode, xPath);
                    } else {
                        result = extractorRegistry
                                .getNonCollectionExtractorForClass(field.getType())
                                .orElseThrow(RuntimeException::new)
                                .apply(rootNode, xPath);

                    }
                    setProperty(instance, field.getName(), result);
                });
        return instance;
    }

    private void setProperty(Object o, String propertyName, Object propertyValue) {
        try {
            BeanUtils.setProperty(o, propertyName, propertyValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
