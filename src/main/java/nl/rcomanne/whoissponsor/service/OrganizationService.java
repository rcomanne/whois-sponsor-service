package nl.rcomanne.whoissponsor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rcomanne.whoissponsor.model.Organization;
import nl.rcomanne.whoissponsor.repository.OrganizationRepository;
import nl.rcomanne.whoissponsor.web.dto.CreateOrganizationDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository repository;

    public List<Organization> findAll() {
        return repository.findAll();
    }

    public Organization findById(final String id) {
        return repository.findById(UUID.fromString(id)).orElseThrow(() -> new NoSuchElementException(String.format("no organization found with id %s", id)));
    }

    public Organization findByName(final String name) {
        return findByNameOptional(name).orElseThrow(() -> new NoSuchElementException(String.format("no organizations found with name %s", name)));
    }

    public Organization findByNameOrCreate(final String name) {
        return findByNameOptional(name).orElseGet(() -> createFromName(name));
    }

    private Optional<Organization> findByNameOptional(final String name) {
        return repository.findFirstByName(name);
    }

    public Organization create(final CreateOrganizationDto dto) {
        // TODO: Implement linking everything on creation like athletes
        if (repository.existsByName(dto.name())) {
            throw new IllegalArgumentException(String.format("Organization %s already exists", dto.name()));
        }
        final var org = new Organization();
        org.setName(dto.name());
        org.setDescription(dto.description());
        return save(org);
    }

    Organization createFromName(final String name) {
        final var org = new Organization(name);
        return save(org);
    }

    public Organization save(final Organization organization) {
        return repository.saveAndFlush(organization);
    }


}
