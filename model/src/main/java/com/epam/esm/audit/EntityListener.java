package com.epam.esm.audit;

import com.epam.esm.entity.AbstractEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * EntityListener class helps to create EntityListener entity
 */
public class EntityListener {

    /**
     * Method onPrePersist method helps set the name of the operation and the persistence time of the current entity
     * before persisting that entity
     * @param abstractEntity AbstractEntity entity
     */
    @PrePersist
    public void onPrePersist(AbstractEntity<Long> abstractEntity) {
        audit("INSERT",abstractEntity);
    }

    /**
     * Method onPreUpdate method helps set the name of the operation and the update time of the current entity
     * before updating that entity
     * @param abstractEntity AbstractEntity entity
     */
    @PreUpdate
    public void onPreUpdate(AbstractEntity<Long> abstractEntity) {
        audit("UPDATE", abstractEntity);
    }

    private void audit(String operation, AbstractEntity<Long> abstractEntity) {
        abstractEntity.setOperation(operation);
        abstractEntity.setTimestamp(LocalDateTime.now());
    }

}
