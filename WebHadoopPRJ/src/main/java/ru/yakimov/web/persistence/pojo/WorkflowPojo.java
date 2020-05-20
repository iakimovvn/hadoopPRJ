package ru.yakimov.web.persistence.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Data
@Builder
public class WorkflowPojo {

    private String uuid;

    private String title;

    private List<String> fromPaths;

    private String toPath;

    private List<TablePojo> tables;

    private List<ColumnPojo> partitions;




}
