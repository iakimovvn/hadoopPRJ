package ru.yakimov.web.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.yakimov.web.persistence.entities.Wfl_user;

import java.util.UUID;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public interface UserRepository extends CrudRepository<Wfl_user, UUID> {
}
