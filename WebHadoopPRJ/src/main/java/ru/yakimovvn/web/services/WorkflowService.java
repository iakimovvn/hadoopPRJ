package ru.yakimovvn.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yakimovvn.web.persistence.entities.*;
import ru.yakimovvn.web.persistence.pojo.ColumnPojo;
import ru.yakimovvn.web.persistence.pojo.TablePojo;
import ru.yakimovvn.web.persistence.pojo.WorkflowPojo;
import ru.yakimovvn.web.persistence.repositories.WorkflowRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final TypeService typeService;

    public List<Workflow> findAll(Wfl_user user, Date createDateFrom, Date createDateTo, Date lastRunFrom, Date lastDateTo, String title, String typeTitle, boolean isDelete){

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

        if(title != null && !title.isEmpty()){
            predicates.add(criteriaBuilder.like(root.get("title"), "%"+title+"%"));

        }

        if(typeTitle != null && !typeTitle.isEmpty()){
            predicates.add(criteriaBuilder.equal(root.get("wfl_type"), typeService.findByTitle(typeTitle)));
        }

        if(!isDelete){
            predicates.add(criteriaBuilder.isFalse(root.get("deleted")));
        }


        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Workflow getByUuid(UUID uuid){
        return workflowRepository.getByUuid(uuid);
    }

    public Workflow save(Workflow workflow){
        return workflowRepository.save(workflow);
    }


    public Workflow getCleanWorkflow(Wfl_user wfl_user){


        Wfl_table table = Wfl_table.builder()
                .name("")
                .primaries(
                        new ArrayList<Wfl_column>(){{
                            add(Wfl_column.builder().name("").type("").build());
                        }}
                ).w_database(
                        Wfl_database.builder().url("").username("").password("").build()
                )
                .build();

        Wfl_config config = Wfl_config.builder()
                .wfl_directories_from(
                        new ArrayList<Wfl_directory_from>(){{
                            add(Wfl_directory_from.builder().path("").build());
                        }}
                )
                .wfl_directory_to(Wfl_directory_to.builder().path("").build())
                .partitions(
                        new ArrayList<Wfl_partition>(){{
                            add(Wfl_partition.builder().name("").type("").build());
                        }}
                ).wfl_tables(new ArrayList<Wfl_table>(){{
                    add(table);
                }})
                .build();

       return Workflow.builder()
                .title("")
                .create_date(new Date())
                .wfl_user(wfl_user)
                .deleted(false)
                .wfl_config(config)
                .build();

    }


    public WorkflowPojo workflowToPojo(Workflow workflow, String logfileUUisStr){

        return WorkflowPojo.builder()
                .uuid(workflow.getUuid().toString())
                .logfileUUID(logfileUUisStr)
                .title(workflow.getTitle())
                .fromPaths(workflow
                        .getWfl_config()
                        .getWfl_directories_from()
                        .stream()
                                .map(Wfl_directory_from::getPath)
                                .collect(Collectors.toList())
                ).toPath(workflow
                        .getWfl_config()
                        .getWfl_directory_to()
                        .getPath()
                ).tables(
                        workflow
                                .getWfl_config()
                                .getWfl_tables()
                                .stream()
                                .map(this::wfl_tableToPojo)
                                .collect(Collectors.toList())
                ).partitions(
                        workflow
                                .getWfl_config()
                                .getPartitions()
                                .stream()
                                .map(this::partitionsToPojo)
                        .collect(Collectors.toList())
                ).build();

    }



    private TablePojo wfl_tableToPojo(Wfl_table wfl_table){
        return TablePojo.builder()
                .name(wfl_table.getName())
                .primaries(
                        wfl_table.getPrimaries()
                                .stream()
                                .map(this::columnToPojo)
                                .collect(Collectors.toList())
                ).databaseUrl(
                        wfl_table
                        .getW_database()
                        .getUrl()
                ).username(
                        wfl_table
                                .getW_database()
                                .getUsername()
                ).password(
                        wfl_table
                                .getW_database()
                                .getPassword()
                ).build();
    }


    private ColumnPojo partitionsToPojo(Wfl_partition partition){
        return ColumnPojo.builder()
                .name(partition.getName())
                .type(partition.getType())
                .build();
    }

    private ColumnPojo columnToPojo(Wfl_column column){
        return ColumnPojo.builder()
                .name(column.getName())
                .type(column.getType())
                .build();
    }
}
