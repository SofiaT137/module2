package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query(value = "(SELECT t.id,t.tag_name,t.operation,t.time_mark," +
            " g_cer.price,count(g_cer.price) as count, sum(g_cer.price) as sum "+
            "FROM tags AS t INNER join gift_certificate_tag AS g_cer_tag on t.id=g_cer_tag.tag_id "+
            "INNER join gift_certificates AS g_cer On g_cer_tag.gift_certificate_id=g_cer.id "+
            "INNER join order_certificate AS ord_cer on g_cer.id = ord_cer.gift_certificate_id "+
            "INNER join orders AS ord ON ord.id=ord_cer.order_id where user_id = :userId "+
            "group by tag_name order by count desc, sum desc) limit 1 ",
            nativeQuery = true)
    List<Tag> findTheMostWidelyUsedUserTagWithHighersOrderCost(Long userId);

    Optional<Tag> findTagByName(String tagName);

    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value = "DELETE FROM gift_certificate_tag WHERE tag_id = :id "
            ,nativeQuery = true)
    void deleteParentRelationShips(Long id);
}
