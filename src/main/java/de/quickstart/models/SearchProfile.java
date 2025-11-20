package de.quickstart.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "search_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String position;

    @Column(name = "position_group")
    private String positionGroup;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "max_age")
    private Integer maxAge;

    @ManyToMany
    private List<Player> favoritePlayers = new ArrayList<>();

    @Embedded
    private PerformanceFilters performanceFilters;
}

