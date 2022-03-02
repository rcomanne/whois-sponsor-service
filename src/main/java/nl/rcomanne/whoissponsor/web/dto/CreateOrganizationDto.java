package nl.rcomanne.whoissponsor.web.dto;

import nl.rcomanne.whoissponsor.model.Organization;

import javax.validation.constraints.NotBlank;

public record CreateOrganizationDto(
        @NotBlank(message = "name is required") String name,
        @NotBlank(message = "description is required") String description
) {
    public Organization toOrganization() {
        final var org = new Organization();
        org.setName(this.name);
        org.setDescription(this.description);
        return org;
    }
}