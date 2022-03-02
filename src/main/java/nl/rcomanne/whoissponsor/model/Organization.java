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
public class Organization {
    @Id
    @GeneratedValue
    @Column(name = "org_id")
    @JsonView(View.Public.class)
    private UUID id;

    @JsonView(View.Public.class)
    private String name;

    @Lob
    @JsonView(View.FullOrganization.class)
    private String description;


    @JsonView(View.FullOrganization.class)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organizations")
    private List<Sponsor> sponsors;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organizations")
    private List<Athlete> athletes;

    public Organization(String name) {
        this.name = name;
        this.sponsors = new ArrayList<>();
        this.athletes = new ArrayList<>();
    }

    public Organization initialize() {
        Hibernate.initialize(this.athletes);
        Hibernate.initialize(this.sponsors);
        return this;
    }
}
