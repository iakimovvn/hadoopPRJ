/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

package ru.yakimovvn.web.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakimovvn.web.persistence.entities.Wfl_type;

import java.util.UUID;

public interface TypeRepository extends JpaRepository<Wfl_type, UUID> {

    Wfl_type findFirstByTitle(String title);
}
