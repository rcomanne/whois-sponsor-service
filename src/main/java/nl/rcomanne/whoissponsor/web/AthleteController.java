package nl.rcomanne.whoissponsor.web;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rcomanne.whoissponsor.model.Athlete;
import nl.rcomanne.whoissponsor.model.View;
import nl.rcomanne.whoissponsor.service.AthleteService;
import nl.rcomanne.whoissponsor.web.dto.CreateAthleteDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/athlete")
@RequiredArgsConstructor
public class AthleteController {

    private final AthleteService service;

    @PostMapping("/create")
    @JsonView(View.FullAthlete.class)
    public ResponseEntity<Athlete> createAthlete(@RequestBody final CreateAthleteDto dto) {
        log.info("creating athlete");
        return ResponseEntity.ok(service.create(dto).initialize());
    }

    @GetMapping("/list")
    @JsonView(View.Public.class)
    public ResponseEntity<List<Athlete>> findAll() {
        log.info("listing all athletes");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findById")
    @JsonView(View.FullAthlete.class)
    public ResponseEntity<Athlete> findById(@RequestParam final String id) {
        log.info("finding athlete with id [{}]", id);
        return ResponseEntity.ok(service.findById(id).initialize());
    }

    @GetMapping("/findByName")
    @JsonView(View.FullAthlete.class)
    public ResponseEntity<Athlete> findByName(@RequestParam(required = false) final String firstName, @RequestParam(required = false) final String lastName) {
        log.info("finding athlete with name(s) [firstname: {}, lastName: {}]", firstName, lastName);
        final Athlete athlete;
        if (Strings.isBlank(firstName) && Strings.isBlank(lastName)) {
            throw new IllegalArgumentException("firstName or lastName is required");
        } else if (Strings.isNotBlank(firstName)) {
            athlete = service.findByFirstName(firstName);
        } else if (Strings.isNotBlank(lastName)) {
            athlete = service.findByLastName(lastName);
        } else {
            athlete = service.findByFullName(firstName, lastName);
        }
        return ResponseEntity.ok(athlete.initialize());
    }
}
