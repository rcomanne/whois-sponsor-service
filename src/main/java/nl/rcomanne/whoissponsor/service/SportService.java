package nl.rcomanne.whoissponsor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rcomanne.whoissponsor.model.Sport;
import nl.rcomanne.whoissponsor.repository.SportRepository;
import nl.rcomanne.whoissponsor.web.dto.CreateSportDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SportService {
    private final SportRepository repository;

    public List<Sport> findAll() {
        return repository.findAll();
    }

    public Sport findById(final String id) {
        return repository.findById(UUID.fromString(id)).orElseThrow(() -> new NoSuchElementException(String.format("no sport found with id %s", id)));
    }

    public Sport findByName(final String name) {
        return findByNameOptional(name).orElseThrow(() -> new NoSuchElementException(String.format("no sport found with name %s", name)));
    }

    public Sport findByNameOrCreate(final String name) {
        // find by exact name first
        final var opt = findByNameOptional(name);

        // return if filled, else create and return
        return opt.orElseGet(() -> create(name));
    }

    private Optional<Sport> findByNameOptional(final String name) {
        // find by exact name first
        var opt = repository.findFirstByName(name);

        // if not found, try partial
        if (opt.isEmpty()) {
            opt = repository.findFirstByNameContaining(name);
        }

        return opt;
    }

    @Transactional
    public Sport create(final CreateSportDto dto) {
        // TODO: Implement linking everything on creation like athletes
        if (repository.existsByName(dto.name())) {
            throw new IllegalArgumentException(String.format("Sport %s already exists", dto.name()));
        }
        return repository.save(dto.toSport());
    }

    private Sport create(final String name) {
        if (repository.existsByName(name)) {
            throw new IllegalArgumentException(String.format("Sport %s already exists", name));
        }
        final var sport = new Sport(name);
        return repository.save(sport);
    }
}
