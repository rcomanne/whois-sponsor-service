package nl.rcomanne.whoissponsor.web;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rcomanne.whoissponsor.model.Sponsor;
import nl.rcomanne.whoissponsor.model.View;
import nl.rcomanne.whoissponsor.service.SponsorService;
import nl.rcomanne.whoissponsor.web.dto.CreateSponsorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/sponsor")
@RequiredArgsConstructor
public class SponsorController {
    private final SponsorService service;

    @PostMapping("/create")
    @JsonView(View.FullSponsor.class)
    public ResponseEntity<Sponsor> create(@Valid @RequestBody final CreateSponsorDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/list")
    @JsonView(View.Public.class)
    public ResponseEntity<List<Sponsor>> findAll() {
        log.info("listing all sponsors");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findById")
    @JsonView(View.FullSponsor.class)
    public ResponseEntity<Sponsor> findById(@RequestParam final String id) {
        log.info("finding sponsor by id [{}]", id);
        return ResponseEntity.ok(service.findById(id).initialize());
    }

    @GetMapping("/findByName")
    @JsonView(View.FullSponsor.class)
    public ResponseEntity<Sponsor> findByName(@RequestParam final String name) {
        log.info("finding sponsor with name [{}]", name);
        return ResponseEntity.ok(service.findByName(name).initialize());
    }
}
