package nl.rcomanne.whoissponsor.web;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rcomanne.whoissponsor.model.Sport;
import nl.rcomanne.whoissponsor.model.View;
import nl.rcomanne.whoissponsor.service.SportService;
import nl.rcomanne.whoissponsor.web.dto.CreateSportDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/sport")
@RequiredArgsConstructor
public class SportController {
    private final SportService service;

    @PostMapping("/create")
    @JsonView(View.FullSport.class)
    public ResponseEntity<Sport> create(@Valid @RequestBody CreateSportDto dto) {
        log.info("creating sport with name [{}]", dto.name());
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/list")
    @JsonView(View.Public.class)
    public ResponseEntity<List<Sport>> findAll() {
        log.info("listing all sports");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findById")
    @JsonView(View.FullSport.class)
    public ResponseEntity<Sport> findById(@RequestParam final String id) {
        log.info("finding sport by id [{}]", id);
        return ResponseEntity.ok(service.findById(id).initialize());
    }

    @GetMapping("/findByName")
    @JsonView(View.FullSport.class)
    public ResponseEntity<Sport> findByName(@RequestParam final String id) {
        log.info("finding sport with name [{}]", id);
        return ResponseEntity.ok(service.findByName(id).initialize());
    }
}
