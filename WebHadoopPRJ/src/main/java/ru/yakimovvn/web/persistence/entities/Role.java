package ru.yakimovvn.web.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ru.yakimovvn.web.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;

import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "tbl_role")
@EqualsAndHashCode(callSuper = true)
public class Role extends PersistableEntity {

    private String role;
}
