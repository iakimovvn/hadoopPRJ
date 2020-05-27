package ru.yakimovvn.web.persistence.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.yakimovvn.web.persistence.entities.User;

import java.util.UUID;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public interface UserRepository extends CrudRepository<User, UUID> {
    User findOneByLogin(String login);
    boolean existsByLogin(String login);

    @Query(
            value = "SELECT tbl_user.hdfs_folder FROM tbl_user " +
                    "WHERE login = :login",

            nativeQuery = true
    )
    String obtainHDFSFolderByLogin(@Param("login")String login);

    @Query(
            value = "SELECT tbl_user.log_folder FROM tbl_user " +
                    "WHERE login = :login",

            nativeQuery = true
    )
    String obtainLogFolderByLogin(@Param("login")String login);
}
