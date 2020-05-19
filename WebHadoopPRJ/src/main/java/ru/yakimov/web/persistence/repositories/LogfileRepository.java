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
import ru.yakimov.web.persistence.entities.Workflow;

import java.util.List;
import java.util.UUID;

public interface LogfileRepository extends JpaRepository<Wfl_logfile, UUID> {

    @Query(
            value = "SELECT wfl_logfile.file FROM wfl_logfile " +
                    "INNER JOIN workflow w on wfl_logfile.workflow = w.uuid " +
                    "WHERE w.uuid = :uuid " +
                    "ORDER BY (wfl_logfile.date) " +
                    "limit 1",

            nativeQuery = true
    )
    String obtainLogfilePathByWorkflowId(@Param("uuid") UUID uuid);

    List<Wfl_logfile> findAllByWorkflow(Workflow workflow);
}
