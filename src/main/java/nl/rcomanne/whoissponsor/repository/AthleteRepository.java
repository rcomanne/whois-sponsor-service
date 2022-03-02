package nl.rcomanne.whoissponsor.repository;

import nl.rcomanne.whoissponsor.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AthleteRepository extends JpaRepository<Athlete, UUID> {
    boolean existsByFirstNameAndLastName(final String firstName, final String lastName);

    Optional<Athlete> findByFirstNameAndLastName(final String firstName, final String lastName);

    Optional<Athlete> findFirstByFirstName(final String firstName);

    List<Athlete> findAllByFirstName(final String firstName);

    Optional<Athlete> findFirstByLastName(final String lastName);

    List<Athlete> findAllByLastName(final String lastName);
}
