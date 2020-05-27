package ru.yakimovvn.web.persistence.entities;

import lombok.*;
import ru.yakimovvn.web.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

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
public class Wfl_logfile extends PersistableEntity {

    private String file;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private boolean writing;

    @ManyToOne
    @JoinColumn(name = "workflow")
    private Workflow workflow;

}
