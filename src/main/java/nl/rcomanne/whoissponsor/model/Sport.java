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
public class Sport {
    @Id
    @GeneratedValue
    @Column(name = "sport_id")
    @JsonView(View.Public.class)
    private UUID id;

    @JsonView(View.Public.class)
    private String name;

    @Lob
    @JsonView(View.FullSport.class)
    private String description;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "sport_athletes",
//            joinColumns = @JoinColumn(name = "sport_id"),
//            inverseJoinColumns = @JoinColumn(name = "athlete_id")
//    )
//    @JsonView(View.FullSport.class)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sports")
    private List<Athlete> athletes;

    public Sport(String name) {
        this.name = name;
        this.athletes = new ArrayList<>();
    }

    public Sport initialize() {
        Hibernate.initialize(this.athletes);
        return this;
    }
}
