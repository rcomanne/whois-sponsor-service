package nl.rcomanne.whoissponsor.repository;

import nl.rcomanne.whoissponsor.model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SponsorRepository extends JpaRepository<Sponsor, UUID> {
    boolean existsByName(final String name);

    Optional<Sponsor> findFirstByName(final String name);

    Optional<Sponsor> findFirstByNameContaining(final String name);
}
