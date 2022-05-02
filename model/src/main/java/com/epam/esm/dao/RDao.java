package com.epam.esm.dao;
import java.util.List;
import java.util.Optional;

public interface RDao<T> {
    /**
     * The method searches the entity object in a destination table by its identifier
     * @param id Long id
     * @return The entity object from table
     */
    Optional<T> getById(long id);

    /**
     * The method searches for all the entity objects in a destination table
     * @return List of the entity objects from table
     */
    List<T> getAll(int pageSize,int pageNumber);
}
