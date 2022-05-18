package com.epam.esm.converter;

import com.epam.esm.entity.AbstractEntity;
import org.springframework.stereotype.Component;

/**
 * The converter class
 */
public interface Converter<T extends AbstractEntity,K> {
    /**
     * Convert AbstractDto Object into AbstractEntity Object
     * @param value AbstractDto Object
     * @return AbstractEntity Object
     */
    T convert(K value);

    /**
     * Convert  AbstractEntity Object into AbstractDto Object
     * @param value AbstractEntity Object
     * @return AbstractDto Object
     */
    K convert(T value);
}

