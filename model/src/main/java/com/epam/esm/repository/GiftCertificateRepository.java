package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * GiftCertificateRepository interface extends JpaRepository and GiftCertificateCustomRepo functionality
 * for the GiftCertificate entity
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate,Long>, GiftCertificateCustomRepo {

    /**
     * Method findByName searches the GiftCertificate entity by its name
     * @param name String name
     * @return Optional of the GiftCertificate entity
     */
    Optional<GiftCertificate> findByName(String name);
}
