package de.quickstart.models;

import jakarta.persistence.*;
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
