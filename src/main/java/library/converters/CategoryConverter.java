package library.converters;


import library.enums.Category;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class CategoryConverter implements AttributeConverter<Category, Integer> {

    @Override
    public Category convertToEntityAttribute(Integer i) {
        if(i == null){
            return null;
        }
        return Arrays.stream(Category.values()).filter(c -> c.getNumber().equals(i))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Podano nieprawidłowy numer enumu: " + i));
    }

    @Override
    public Integer convertToDatabaseColumn(Category category) {
        return category.getNumber();
    }
}

