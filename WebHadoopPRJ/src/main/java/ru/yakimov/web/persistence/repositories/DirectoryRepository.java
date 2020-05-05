/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

package ru.yakimov.web.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakimov.web.persistence.entities.Wfl_directory;

import java.util.UUID;

public interface DirectoryRepository extends JpaRepository<Wfl_directory, UUID> {
}
