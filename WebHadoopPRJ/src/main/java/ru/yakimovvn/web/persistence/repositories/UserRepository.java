package ru.yakimovvn.web.persistence.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.yakimovvn.web.persistence.entities.Wfl_user;

import java.util.UUID;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public interface UserRepository extends CrudRepository<Wfl_user, UUID> {
    Wfl_user findOneByLogin(String login);
    boolean existsByLogin(String login);

    @Query(
            value = "SELECT wfl_user.hdfs_folder FROM wfl_user " +
                    "WHERE login = :login",

            nativeQuery = true
    )
    String obtainHDFSFolderByLogin(@Param("login")String login);

    @Query(
            value = "SELECT wfl_user.log_folder FROM wfl_user " +
                    "WHERE login = :login",

            nativeQuery = true
    )
    String obtainLogFolderByLogin(@Param("login")String login);
}
