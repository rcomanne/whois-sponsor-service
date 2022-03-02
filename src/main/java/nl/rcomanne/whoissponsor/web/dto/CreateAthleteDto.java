package nl.rcomanne.whoissponsor.web.dto;

import java.util.List;

public record CreateAthleteDto(
        String firstName,
        String lastName,
        String sport,
        String organization,
        String sponsor,
        List<String> sports,
        List<String> organizations,
        List<String> sponsors
) {
}
