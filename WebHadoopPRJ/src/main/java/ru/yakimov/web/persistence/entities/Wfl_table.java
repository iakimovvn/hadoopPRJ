package ru.yakimov.web.persistence.entities;

import lombok.*;
import org.springframework.stereotype.Service;
import ru.yakimov.web.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Wfl_table extends PersistableEntity {

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wfl_table")
    private List<Wfl_column> primaries = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "database")
    private Wfl_database w_database;

    @ManyToOne
    @JoinColumn(name = "wfl_config",insertable=false, updatable=false)
    private Wfl_config wfl_config;
}
