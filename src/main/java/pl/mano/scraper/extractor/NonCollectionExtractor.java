package pl.mano.scraper.extractor;

import org.apache.commons.beanutils.BeanUtils;
import org.htmlcleaner.TagNode;
import pl.mano.annotation.XPath;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

class NonCollectionExtractor implements Extractor<Object> {

    private final Class<?> clazz;

    private final ExtractorRegistry extractorRegistry;

    NonCollectionExtractor(Class<?> clazz, ExtractorRegistry extractorRegistry) {
        this.clazz = clazz;
        this.extractorRegistry = extractorRegistry;
    }

    @Override
    public Object apply(TagNode rootNode, String rootXPath) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(XPath.class))
                    .forEach(field -> {
                        String xPath = rootXPath + field.getAnnotation(XPath.class).value();
                        Extractor<?> extractor;
                        if (field.getType().equals(List.class)) {
                            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                            Class<?> listClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                            extractor = extractorRegistry.getCollectionExtractorForClass(listClass);
                        } else {
                            extractor = extractorRegistry.getNonCollectionExtractorForClass(field.getType());
                        }
                        setProperty(instance, field.getName(), extractor.apply(rootNode, xPath));
                    });
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
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
