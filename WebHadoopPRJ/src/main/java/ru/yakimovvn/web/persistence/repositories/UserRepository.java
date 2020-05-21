package ru.yakimovvn.web.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
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
}
