package com.epam.esm.entity;

import com.epam.esm.audit.EntityListener;

import javax.persistence.*;
import java.util.Objects;

/**
 * The abstract class AbstractEntity presents creation of the abstract entity
 * @param <K>
 */
@MappedSuperclass
@EntityListeners(EntityListener.class)
public abstract class AbstractEntity<K> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
