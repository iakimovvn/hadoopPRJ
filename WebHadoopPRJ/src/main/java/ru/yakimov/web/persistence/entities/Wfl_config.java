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
public class Wfl_config  extends PersistableEntity {

    @ManyToMany
    @JoinTable(name="wfl_config_directory_from", joinColumns=
    @JoinColumn(name="wfl_config", referencedColumnName="id"), inverseJoinColumns=
    @JoinColumn(name="Wfl_directory", referencedColumnName="id"))
    private Set<Wfl_directory> wfl_directories_from = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "wfl_directory_to")
    private Wfl_directory wfl_directory_to;

    @ManyToMany
    @JoinTable(name="wfl_config_table", joinColumns=
    @JoinColumn(name="wfl_config", referencedColumnName="id"), inverseJoinColumns=
    @JoinColumn(name="Wfl_table", referencedColumnName="id"))
    private Set<Wfl_table> wfl_tables = new HashSet<>();

    @ManyToMany
    @JoinTable(name="wfl_config_partitions", joinColumns=
    @JoinColumn(name="wfl_config", referencedColumnName="id"), inverseJoinColumns=
    @JoinColumn(name="wfl_column", referencedColumnName="id"))
    private Set<Wfl_column> partitions = new HashSet<>();
}
