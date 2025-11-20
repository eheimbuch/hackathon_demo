package de.quickstart.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "positions")
@Setter
@Getter
public class Position {
    @Id
    private Long id;

    private String name;

    @Column(name = "position_group")
    private String group;
}
