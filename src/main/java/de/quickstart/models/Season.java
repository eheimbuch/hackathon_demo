package de.quickstart.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

