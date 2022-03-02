package nl.rcomanne.whoissponsor.repository;

import nl.rcomanne.whoissponsor.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    boolean existsByName(final String name);

    Optional<Organization> findFirstByName(final String name);

    Optional<Organization> findFirstByNameContaining(final String name);
}
