package nl.rcomanne.whoissponsor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rcomanne.whoissponsor.model.Athlete;
import nl.rcomanne.whoissponsor.model.Organization;
import nl.rcomanne.whoissponsor.model.Sponsor;
import nl.rcomanne.whoissponsor.model.Sport;
import nl.rcomanne.whoissponsor.repository.AthleteRepository;
import nl.rcomanne.whoissponsor.web.dto.CreateAthleteDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AthleteService {

    private final AthleteRepository repository;

    private final OrganizationService organizationService;
    private final SportService sportService;
    private final SponsorService sponsorService;

    public Athlete create(final CreateAthleteDto dto) {
        if (existsByName(dto.firstName(), dto.lastName())) {
            throw new IllegalArgumentException(String.format("Athlete %s %s already exists", dto.firstName(), dto.lastName()));
        }

        final var athlete = save(new Athlete(dto.firstName(), dto.lastName()));

        processOrganizations(dto, athlete);
        processSports(dto, athlete);
        processSponsors(dto, athlete);

        return save(athlete);
    }

    public void processOrganizations(CreateAthleteDto dto, Athlete athlete) {
        // if org(s) are supplied, add it/them
        final List<Organization> organizations = new ArrayList<>();
        if (Strings.isNotBlank(dto.organization())) {
            organizations.add(organizationService.findByNameOrCreate(dto.organization()));
        }

        if (dto.organizations() != null && !dto.organizations().isEmpty()) {
            dto.sports().forEach(s -> organizations.add(organizationService.findByNameOrCreate(s)));
        }
        athlete.setOrganizations(organizations);
    }

    public void processSports(CreateAthleteDto dto, Athlete athlete) {
        // if sport(s) are provided, add it/them
        final List<Sport> sports = new ArrayList<>();
        if (Strings.isNotBlank(dto.sport())) {
            sports.add(sportService.findByNameOrCreate(dto.sport()));
        }
        if (dto.sports() != null && !dto.sports().isEmpty()) {
            dto.sports().forEach(s -> sports.add(sportService.findByNameOrCreate(s)));
        }
        athlete.setSports(sports);
    }

    public void processSponsors(CreateAthleteDto dto, Athlete athlete) {
        // if personal sponsor(s) are provided, add them
        final List<Sponsor> sponsors = new ArrayList<>();
        if (Strings.isNotBlank(dto.sponsor())) {
            sponsors.add(sponsorService.findByNameOrCreate(dto.sponsor()));
        }
        if (dto.sponsors() != null && !dto.sponsors().isEmpty()) {
            dto.sponsors().forEach(s -> sponsors.add(sponsorService.findByNameOrCreate(s)));
        }
        athlete.setSponsors(sponsors);
    }

    public List<Athlete> findAll() {
        return repository.findAll();
    }

    public Athlete findById(final String id) {
        return repository.findById(UUID.fromString(id)).orElseThrow(() -> new NoSuchElementException(String.format("no athlete found with id %s", id)));
    }

    public Athlete findByFirstName(final String name) {
        return repository.findFirstByFirstName(name)
                .orElseThrow(() -> new NoSuchElementException(String.format("no athlete found with first name %s", name)));
    }

    public List<Athlete> findAllByFirstName(final String name) {
        return repository.findAllByFirstName(name);
    }

    public Athlete findByLastName(final String name) {
        return repository.findFirstByLastName(name)
                .orElseThrow(() -> new NoSuchElementException(String.format("no athlete found with last name %s", name)));
    }

    public List<Athlete> findAllByLastName(final String lastName) {
        return repository.findAllByLastName(lastName);
    }

    public Athlete findByFullName(final String firstName, final String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NoSuchElementException(String.format("no athlete found with first name %s and last name %s", firstName, lastName)));
    }

    public boolean existsByName(final String firstName, final String lastName) {
        return repository.existsByFirstNameAndLastName(firstName, lastName);
    }

    public Athlete save(final Athlete athlete) {
        return repository.saveAndFlush(athlete);
    }
}
