package com.epam.esm.audit;

import com.epam.esm.entity.AbstractEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class EntityListener {

    @PrePersist
    public void onPrePersist(AbstractEntity<Long> abstractEntity) {
        audit("INSERT",abstractEntity);
    }
    @PreUpdate
    public void onPreUpdate(AbstractEntity<Long> abstractEntity) {
        audit("UPDATE", abstractEntity);
    }

    private void audit(String operation, AbstractEntity<Long> abstractEntity) {
        abstractEntity.setOperation(operation);
        abstractEntity.setTimestamp(LocalDateTime.now());
    }

}
