package nl.rcomanne.whoissponsor.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Athlete {
    @Id
    @GeneratedValue
    @Column(name = "athlete_id")
    @JsonView(View.Public.class)
    private UUID id;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "athletes")
    @JsonView(View.FullAthlete.class)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "athletes_organizations",
            joinColumns = @JoinColumn(name = "athlete_id"),
            inverseJoinColumns = @JoinColumn(name = "org_id")
    )
    private List<Organization> organizations;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "athletes")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "athletes_sports",
            joinColumns = @JoinColumn(name = "athlete_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_id")
    )
    @JsonView(View.FullAthlete.class)
    private List<Sport> sports;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "athletes")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "athletes_sponsors",
            joinColumns = @JoinColumn(name = "athlete_id"),
            inverseJoinColumns = @JoinColumn(name = "sponsor_id")
    )
    @JsonView(View.FullAthlete.class)
    private List<Sponsor> sponsors;

    @JsonView(View.Public.class)
    private String firstName;

    @JsonView(View.Public.class)
    private String lastName;

    public Athlete(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organizations = new ArrayList<>();
        this.sports = new ArrayList<>();
        this.sponsors = new ArrayList<>();
    }

    public Athlete initialize() {
        Hibernate.initialize(this.organizations);
        Hibernate.initialize(this.sponsors);
        Hibernate.initialize(this.sports);
        return this;
    }
}
