/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

package ru.yakimov.web.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakimov.web.persistence.entities.Wfl_table;

import java.util.UUID;

public interface TableRepository extends JpaRepository<Wfl_table, UUID> {
}
