package nl.rcomanne.whoissponsor.repository;

import nl.rcomanne.whoissponsor.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SportRepository extends JpaRepository<Sport, UUID> {
    boolean existsByName(final String name);

    Optional<Sport> findFirstByName(final String name);

    Optional<Sport> findFirstByNameContaining(final String name);
}
