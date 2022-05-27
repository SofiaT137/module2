package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GiftCertificateDao extends JpaRepository<GiftCertificate,Long>, GiftCertificateDaoFilter{

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update GiftCertificate gs " +
            "set gs.duration = :duration " +
            "where gs.id = :id")
    int update(int duration, Long id);
}
