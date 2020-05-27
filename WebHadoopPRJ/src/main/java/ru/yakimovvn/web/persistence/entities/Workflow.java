package ru.yakimovvn.web.persistence.entities;

import lombok.*;
import ru.yakimovvn.web.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class Workflow extends PersistableEntity {

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_run_date;

    @ManyToOne
    @JoinColumn(name = "wfl_type")
    private Wfl_type wfl_type;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wfl_config")
    private Wfl_config wfl_config;

    @ManyToOne
    @JoinColumn(name = "wf_user")
    private User user;

    private boolean deleted;

    private boolean run;


}
