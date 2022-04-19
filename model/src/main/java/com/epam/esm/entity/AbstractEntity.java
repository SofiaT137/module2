package com.epam.esm.entity;

import java.util.Objects;

/**
 * The abstract class AbstractEntity presents creation of the abstract entity
 * @param <K>
 */
public abstract class AbstractEntity<K> {

    private Long id;

    protected AbstractEntity(){}

    protected AbstractEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;
        AbstractEntity<?> that = (AbstractEntity<?>) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
