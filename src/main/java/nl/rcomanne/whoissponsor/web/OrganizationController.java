package nl.rcomanne.whoissponsor.web;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rcomanne.whoissponsor.model.Organization;
import nl.rcomanne.whoissponsor.model.View;
import nl.rcomanne.whoissponsor.service.OrganizationService;
import nl.rcomanne.whoissponsor.web.dto.CreateOrganizationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService service;

    @PostMapping("/create")
    @JsonView(View.FullOrganization.class)
    public ResponseEntity<Organization> create(@Valid @RequestBody final CreateOrganizationDto dto) {
        log.info("creating organization with name [{}]", dto.name());
        return ResponseEntity.ok(service.create(dto).initialize());
    }

    @GetMapping("/list")
    @JsonView(View.Public.class)
    public ResponseEntity<List<Organization>> findAll() {
        log.info("listing all organizations");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findById")
    @JsonView(View.FullOrganization.class)
    public ResponseEntity<Organization> findById(@RequestParam final String id) {
        log.info("finding organization by id [{}]", id);
        return ResponseEntity.ok(service.findById(id).initialize());
    }

    @GetMapping("/findByName")
    @JsonView(View.FullOrganization.class)
    public ResponseEntity<Organization> findByName(@RequestParam final String name) {
        log.info("finding organization with name [{}]", name);
        return ResponseEntity.ok(service.findByName(name).initialize());
    }
}
