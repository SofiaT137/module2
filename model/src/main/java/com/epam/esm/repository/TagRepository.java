package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * TagRepository interface implements JpaRepository functionality for the Tag entity
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    /**
     * Method findTheMostWidelyUsedUserTagWithHighersOrderCost searches the widely used user tag with higher
     * order cost
     * @param userId User id (Long value)
     * @return Optional of the Tag entity
     */
    @Query(value = "(SELECT t.id,t.tag_name,t.created_date,t.last_modified_date,t.last_modified_by" +
            " g_cer.price,count(g_cer.price) as count, sum(g_cer.price) as sum "+
            "FROM tags AS t INNER join gift_certificate_tag AS g_cer_tag on t.id=g_cer_tag.tag_id "+
            "INNER join gift_certificates AS g_cer On g_cer_tag.gift_certificate_id=g_cer.id "+
            "INNER join order_certificate AS ord_cer on g_cer.id = ord_cer.gift_certificate_id "+
            "INNER join orders AS ord ON ord.id=ord_cer.order_id where user_id = :userId "+
            "group by tag_name order by count desc, sum desc) limit 1 ",
            nativeQuery = true)
    Optional<Tag> findTheMostWidelyUsedUserTagWithHighersOrderCost(Long userId);


    /**
     * Method findTagByTagName searches the Tag entity by its name
     * @param tagName String tag name
     * @return Optional of the Tag entity
     */
    Optional<Tag> findTagByName(String tagName);

    /**
     * Method findTagByTagName deletes the parents of the Tag entity
     * @param id Tag id (Long value)
     */
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value = "DELETE FROM gift_certificate_tag WHERE tag_id = :id "
            ,nativeQuery = true)
    void deleteParentRelationShips(Long id);
}
