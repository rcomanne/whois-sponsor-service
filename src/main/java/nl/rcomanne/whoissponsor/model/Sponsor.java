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
public class Sponsor {

    @Id
    @GeneratedValue
    @Column(name = "sponsor_id")
    @JsonView(View.Public.class)
    private UUID id;

    @JsonView(View.Public.class)
    private String name;

    @Lob
    @JsonView(View.FullSponsor.class)
    private String description;

    @JsonView(View.FullSponsor.class)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sponsors")
    private List<Athlete> athletes;

    @JsonView(View.FullSponsor.class)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "sponsors_organization",
            joinColumns = @JoinColumn(name = "sponsor_id"),
            inverseJoinColumns = @JoinColumn(name = "org_id")
    )
    private List<Organization> organizations;

    public Sponsor(String name) {
        this.name = name;
        this.athletes = new ArrayList<>();
    }

    public Sponsor initialize() {
        Hibernate.initialize(this.athletes);
        Hibernate.initialize(this.organizations);
        return this;
    }
}
