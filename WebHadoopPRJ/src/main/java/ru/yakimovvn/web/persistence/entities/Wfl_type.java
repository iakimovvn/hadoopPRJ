package ru.yakimovvn.web.persistence.entities;

import lombok.*;
import ru.yakimovvn.web.persistence.entities.utils.PersistableEntity;

import javax.persistence.Entity;

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
public class Wfl_type extends PersistableEntity {

    String title;

    String class_name;
}
