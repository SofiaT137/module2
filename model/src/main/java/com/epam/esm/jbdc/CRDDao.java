//package com.epam.esm.jbdc;
//
//import com.epam.esm.exceptions.DaoException;
//
//import java.util.List;
//
///**
// * CRDDao interface features CRD DAO functionality
// * @param <T> The entity object from table
// */
//public interface CRDDao<T>{
//    /**
//     * The method inserts the entity object to a destination table
//     * @param entity The entity object from table
//     */
//    void insert(T entity);
//
//    /**
//     * The method searches the entity object in a destination table by its identifier
//     * @param id Long id
//     * @return The entity object from table
//     */
//    T getById(long id);
//
//    /**
//     * The method searches for all the entity objects in a destination table
//     * @return List of the entity objects from table
//     */
//    List<T> getAll();
//
//    /**
//     * The method removes the entity object in a destination table by its identifier
//     * @param id Long id
//     */
//    void deleteByID(long id);
//}
