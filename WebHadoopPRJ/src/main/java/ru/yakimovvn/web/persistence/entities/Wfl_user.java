package ru.yakimovvn.web.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.yakimovvn.web.persistence.entities.enums.Role;
import ru.yakimovvn.web.persistence.entities.utils.PersistableEntity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Wfl_user extends PersistableEntity {

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String hdfs_folder;

    private String log_folder;

    @Enumerated(EnumType.STRING)
    private Role role;

}