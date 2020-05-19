package ru.yakimov.web.persistence.entities;

import lombok.*;
import ru.yakimov.web.persistence.entities.utils.PersistableEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Wfl_column extends PersistableEntity {

    private String name;

    private String type;

    @ManyToOne
    @JoinColumn(name = "wfl_table", insertable=false, updatable=false)
    private Wfl_table wfl_table;

}
