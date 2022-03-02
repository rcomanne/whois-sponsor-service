package nl.rcomanne.whoissponsor.web.dto;

import nl.rcomanne.whoissponsor.model.Sport;

import javax.validation.constraints.NotBlank;

public record CreateSportDto(
        @NotBlank(message = "name cannot be empty") String name,
        @NotBlank(message = "description cannot be empty") String description
) {
    public Sport toSport() {
        final var sport = new Sport();
        sport.setName(this.name);
        sport.setDescription(this.name);
        return sport;
    }
}
