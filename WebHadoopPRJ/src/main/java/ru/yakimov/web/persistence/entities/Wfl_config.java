package ru.yakimov.web.persistence.entities;

import lombok.*;
import ru.yakimov.web.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Wfl_config  extends PersistableEntity {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wfl_config")
    private List<Wfl_directory_from> wfl_directories_from = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wfl_directory_to")
    private Wfl_directory_to wfl_directory_to;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wfl_config")
    private List<Wfl_table> wfl_tables = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wfl_config")
    private List<Wfl_partition> partitions = new ArrayList<>();
}
