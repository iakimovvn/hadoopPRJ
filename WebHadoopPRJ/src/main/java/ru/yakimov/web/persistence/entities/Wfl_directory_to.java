package ru.yakimov.web.persistence.entities;

import lombok.*;
import ru.yakimov.web.persistence.entities.utils.PersistableEntity;

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
public class Wfl_directory_to extends PersistableEntity {

    private String path;

}
