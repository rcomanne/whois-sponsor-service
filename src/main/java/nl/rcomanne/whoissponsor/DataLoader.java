package nl.rcomanne.whoissponsor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rcomanne.whoissponsor.model.Athlete;
import nl.rcomanne.whoissponsor.model.Organization;
import nl.rcomanne.whoissponsor.model.Sponsor;
import nl.rcomanne.whoissponsor.model.Sport;
import nl.rcomanne.whoissponsor.service.AthleteService;
import nl.rcomanne.whoissponsor.service.OrganizationService;
import nl.rcomanne.whoissponsor.service.SponsorService;
import nl.rcomanne.whoissponsor.service.SportService;
import nl.rcomanne.whoissponsor.web.dto.CreateOrganizationDto;
import nl.rcomanne.whoissponsor.web.dto.CreateSponsorDto;
import nl.rcomanne.whoissponsor.web.dto.CreateSportDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@Transactional
@Profile(value = {"dev"})
@RequiredArgsConstructor
public class DataLoader {
    public static final String DEFAULT_DESCRIPTION = "default description";
    private final AthleteService athleteService;
    private final OrganizationService organizationService;
    private final SponsorService sponsorService;
    private final SportService sportService;

    private final Random rand = new Random();

    @PostConstruct
    void postConstruct() {
        log.debug("loading initial dev data");

        final var sports = createSports();
        final var sponsors = createSponsors();
        final var organizations = createOrganizations();

        final var athletes = createAthletes();
        athletes.forEach(athlete -> addSportToAthlete(athlete, sports));
        athletes.forEach(athlete -> addSponsorToAthlete(athlete, sponsors));
        athletes.forEach(athlete -> addOrganizationToAthlete(athlete, organizations));
    }

    public void addSportToAthlete(final Athlete athlete, final List<Sport> sports) {
        log.debug("start addSportToAthlete");
        athlete.setSports(List.of(sports.get(rand.nextInt(sports.size()))));
        athleteService.save(athlete);
        log.debug("end addSportToAthlete");
    }

    public void addSponsorToAthlete(final Athlete athlete, final List<Sponsor> sponsors) {
        log.debug("start addSponsorToAthlete");
        athlete.setSponsors(List.of(sponsors.get(rand.nextInt(sponsors.size()))));
        athleteService.save(athlete);
        log.debug("end addSponsorToAthlete");
    }

    public void addOrganizationToAthlete(final Athlete athlete, final List<Organization> organizations) {
        log.debug("start addOrganizationToAthlete");
        athlete.setOrganizations(List.of(organizations.get(rand.nextInt(organizations.size()))));
        athleteService.save(athlete);
        log.debug("end addOrganizationToAthlete");
    }

    public List<Athlete> createAthletes() {
        log.debug("creating athletes");
        final var names = List.of(
                "Max Verstappen",
                "Lewis Hamilton"
        );

        names.forEach(n -> {
            final var athlete = new Athlete();
            athlete.setFirstName(n.split(" ")[0]);
            athlete.setLastName(n.split(" ")[1]);
            athleteService.save(athlete);
        });
        return athleteService.findAll();
    }

    public List<Sport> createSports() {
        log.debug("creating sports");
        final var names = List.of(
                "Racing",
                "Soccer"
        );

        names.forEach(s -> sportService.create(new CreateSportDto(s, DEFAULT_DESCRIPTION)));
        return sportService.findAll();
    }

    public List<Sponsor> createSponsors() {
        log.debug("creating sponsors");
        final var names = List.of(
                "Gazprom",
                "Aramco",
                "Petronas"
        );

        names.forEach(s -> sponsorService.create(new CreateSponsorDto(s, DEFAULT_DESCRIPTION)));
        return sponsorService.findAll();
    }

    public List<Organization> createOrganizations() {
        log.debug("creating organizations");
        final var names = List.of(
                "Red Bull",
                "Mercedes",
                "Ferrari",
                "FIA",
                "Formula One Management",
                "Ajax",
                "UEFA"
        );

        names.forEach(o -> organizationService.create(new CreateOrganizationDto(o, DEFAULT_DESCRIPTION)));
        return organizationService.findAll();
    }
}
