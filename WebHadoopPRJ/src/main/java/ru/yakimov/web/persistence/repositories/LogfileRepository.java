/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

package ru.yakimov.web.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yakimov.web.persistence.entities.Wfl_logfile;

import java.util.UUID;

public interface LogfileRepository extends JpaRepository<Wfl_logfile, UUID> {

    @Query(
            value = "SELECT wfl_logfile.file FROM wfl_logfile " +
                    "INNER JOIN workflow w on wfl_logfile.workflow_id = w.id " +
                    "WHERE w.id = :id " +
                    "ORDER BY (wfl_logfile.date) " +
                    "limit 1",

            nativeQuery = true
    )
    String obtainLogfilePathByWorkflowId(@Param("id") UUID id);
}
