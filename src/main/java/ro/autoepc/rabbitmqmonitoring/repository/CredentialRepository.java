package ro.autoepc.rabbitmqmonitoring.repository;

import org.springframework.transaction.annotation.Transactional;
import ro.autoepc.rabbitmqmonitoring.domain.Credential;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Credential entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    @Transactional
    Credential findByHostId(Long id);
}
