package pl.mano.scraper.extractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.htmlcleaner.TagNode;
import pl.mano.annotation.XPath;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
class NonCollectionExtractor implements Extractor<Object> {

    private final Class<?> clazz;
    private final ExtractorRegistry extractorRegistry;


    @Override
    public Object apply(TagNode rootNode, String rootXPath) {
        try {
            log.debug("Scraping root xPath [{}]", rootXPath);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(XPath.class))
                    .forEach(field -> {
                        String xPath = rootXPath + field.getAnnotation(XPath.class).value();
                        Object propertyValue = getExtractorBy(field).apply(rootNode, xPath);
                        setProperty(instance, field.getName(), propertyValue);
                    });
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private Extractor<?> getExtractorBy(Field field) {
        if (List.class.isAssignableFrom(field.getType())) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Class<?> listClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            return extractorRegistry.getCollectionExtractorForClass(listClass);
        }
        return extractorRegistry.getNonCollectionExtractorForClass(field.getType());
    }

    private void setProperty(Object o, String propertyName, Object propertyValue) {
        try {
            BeanUtils.setProperty(o, propertyName, propertyValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.warn(e.getMessage(), e);
        }
    }

}
