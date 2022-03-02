package nl.rcomanne.whoissponsor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rcomanne.whoissponsor.model.Sponsor;
import nl.rcomanne.whoissponsor.repository.SponsorRepository;
import nl.rcomanne.whoissponsor.web.dto.CreateSponsorDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SponsorService {

    private final SponsorRepository repository;

    public List<Sponsor> findAll() {
        return repository.findAll();
    }

    public Sponsor findById(final String id) {
        return repository.findById(UUID.fromString(id)).orElseThrow(() -> new NoSuchElementException(String.format("no sponsor found with id %s", id)));
    }

    public Sponsor findByNameOrCreate(final String name) {
        return findByNameOpt(name).orElseGet(() -> create(name));
    }

    public Sponsor findByName(final String name) {
        // return if filled, else create and return
        return findByNameOpt(name).orElseThrow(() -> new NoSuchElementException(String.format("no sponsor found with name %s", name)));
    }

    private Optional<Sponsor> findByNameOpt(final String name) {
        // find by exact name first
        var opt = repository.findFirstByName(name);

        // if not found, try partial
        if (opt.isEmpty()) {
            opt = repository.findFirstByNameContaining(name);
        }

        return opt;
    }

    public Sponsor create(final CreateSponsorDto dto) {
        // TODO: Implement linking everything on creation like athletes
        if (repository.existsByName(dto.name())) {
            throw new IllegalArgumentException(String.format("Sponsor %s already exists", dto.name()));
        }
        return save(dto.toSponsor());
    }

    Sponsor create(final String name) {
        if (repository.existsByName(name)) {
            throw new IllegalArgumentException(String.format("Sponsor %s already exists", name));
        }
        final var sponsor = new Sponsor(name);
        return save(sponsor);
    }

    public Sponsor save(final Sponsor sponsor) {
        return repository.saveAndFlush(sponsor);
    }
}
