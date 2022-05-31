package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate,Long>, GiftCertificateFilter {

    Optional<GiftCertificate> findByName(String name);
}
