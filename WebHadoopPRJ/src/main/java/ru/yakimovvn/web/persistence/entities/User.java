package ru.yakimovvn.web.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.yakimovvn.web.persistence.entities.utils.PersistableEntity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tbl_user")
@EqualsAndHashCode(callSuper = true)
public class User extends PersistableEntity {

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String hdfs_folder;

    private String log_folder;

    @ManyToMany
    @JoinTable(name="tbl_role_user", joinColumns=
    @JoinColumn(name="user_uuid", referencedColumnName="uuid"), inverseJoinColumns=
    @JoinColumn(name="role_uuid", referencedColumnName="uuid"))
    private Set<Role> roles = new HashSet<>();

}