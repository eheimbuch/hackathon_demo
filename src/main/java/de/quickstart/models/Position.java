package de.quickstart.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "positions")
@Setter
@Getter
public class Position {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "position_group")
    private String group;
}
