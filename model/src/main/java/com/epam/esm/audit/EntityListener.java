package com.epam.esm.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class EntityListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityListener.class);

    @PrePersist
    public void onPrePersist(Object object) {
        audit("INSERT " + object);
    }

    @PreUpdate
    public void onPreUpdate(Object object) {
        audit("UPDATE " + object);
    }

    @PreRemove
    public void onPreRemove(Object object) {
        audit("DELETE " + object);
    }

    private void audit(String operation) {
        LOGGER.info("Operation: " + operation + "; Data: " + getLocalDateTime());
    }

    private LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }
}
