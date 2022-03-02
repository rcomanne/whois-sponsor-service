package nl.rcomanne.whoissponsor.web.dto;

import nl.rcomanne.whoissponsor.model.Sponsor;

import javax.validation.constraints.NotBlank;

public record CreateSponsorDto(
        @NotBlank(message = "name cannot be emtpy") String name,
        @NotBlank(message = "description cannot be emtpy") String description
) {
    public Sponsor toSponsor() {
        final var sponsor = new Sponsor();
        sponsor.setName(this.name);
        sponsor.setDescription(this.description);
        return sponsor;
    }
}
