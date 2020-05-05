package ru.yakimov.web.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yakimov.web.persistence.entities.Wfl_type;
import ru.yakimov.web.persistence.entities.Wfl_user;
import ru.yakimov.web.persistence.entities.Workflow;
import ru.yakimov.web.persistence.repositories.WorkflowRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowService {

    @PersistenceContext
    private EntityManager entityManager;

    private final WorkflowRepository workflowRepository;

    public List<Workflow> findAll(Wfl_user user, Date createDateFrom, Date createDateTo, Date lastRunFrom, Date lastDateTo, String title, Wfl_type type){

        if(user == null) {
            return new ArrayList<>();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Workflow> criteriaQuery = criteriaBuilder.createQuery(Workflow.class);
        Root<Workflow> root = criteriaQuery.from(Workflow.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(root.get("wfl_user"), user));

        if(createDateFrom != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("create_date"), createDateFrom));
        }

        if(createDateTo != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("create_date"), createDateTo));
        }

        if(lastRunFrom != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("last_run_date"), lastRunFrom));
        }

        if(lastDateTo != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("last_run_date"), lastDateTo));
        }

        if(title != null){
            predicates.add(criteriaBuilder.equal(root.get("title"), title));

        }

        if(type != null){
            predicates.add(criteriaBuilder.equal(root.get("wfl_type"), type));
        }

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
