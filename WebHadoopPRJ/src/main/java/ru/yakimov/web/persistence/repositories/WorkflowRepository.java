package ru.yakimov.web.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakimov.web.persistence.entities.Workflow;

import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {

}
