package ru.yakimov.web.persistence.entities;

import lombok.*;
import ru.yakimov.web.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Wfl_table extends PersistableEntity {

    private String name;

    @ManyToMany
    @JoinTable(name="wfl_table_primaries", joinColumns=
    @JoinColumn(name="wfl_table", referencedColumnName="id"), inverseJoinColumns=
    @JoinColumn(name="wfl_column", referencedColumnName="id"))
    private Set <Wfl_column> primaries = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "database")
    private Wfl_database w_database;
}
