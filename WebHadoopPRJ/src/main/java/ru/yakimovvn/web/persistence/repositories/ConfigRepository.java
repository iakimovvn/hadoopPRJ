/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

package ru.yakimovvn.web.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakimovvn.web.persistence.entities.Wfl_config;

import java.util.UUID;

public interface ConfigRepository extends JpaRepository<Wfl_config, UUID> {
}
