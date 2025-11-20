package de.quickstart.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "competitions")
@Getter
@Setter
public class Competition {
    @Id
    private Long id; // Competition ID

    private String name;
}
