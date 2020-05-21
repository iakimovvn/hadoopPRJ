package ru.yakimov.web.persistence.entities;

import lombok.*;
import ru.yakimov.web.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;
import java.util.Date;
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
public class Workflow extends PersistableEntity {

    private String title;

    @Temporal(TemporalType.DATE)
    private Date create_date;

    @Temporal(TemporalType.DATE)
    private Date last_run_date;

    @ManyToOne
    @JoinColumn(name = "wfl_type")
    private Wfl_type wfl_type;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wfl_config")
    private Wfl_config wfl_config;

    @ManyToOne
    @JoinColumn(name = "wfl_user")
    private Wfl_user wfl_user;

    private boolean deleted;

    private boolean run;


}
