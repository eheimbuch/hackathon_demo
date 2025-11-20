package de.quickstart.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seasons")
@Getter
@Setter
public class Season {
    @Id
    private Long id; // Season ID

    private String name;
}

