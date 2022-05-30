package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate,Long>, GiftCertificateFilter {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update GiftCertificate gs " +
            "set gs.duration = :duration " +
            "where gs.id = :id")
    int update(int duration, Long id);

    Optional<GiftCertificate> findByName(String name);
}
