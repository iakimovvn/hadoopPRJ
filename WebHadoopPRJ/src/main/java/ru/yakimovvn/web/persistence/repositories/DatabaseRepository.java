/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

package ru.yakimovvn.web.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakimovvn.web.persistence.entities.Wfl_database;

import java.util.UUID;

public interface DatabaseRepository extends JpaRepository<Wfl_database, UUID> {
}
