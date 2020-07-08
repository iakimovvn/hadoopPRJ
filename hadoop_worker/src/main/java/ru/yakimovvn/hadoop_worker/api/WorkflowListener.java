package ru.yakimovvn.hadoop_worker.api;

import ru.yakimovvn.api.pojo.WorkflowPojo;

import java.util.Map;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public abstract class WorkflowListener extends ComponentBase {
    public WorkflowListener(Map<String, Object> parameters) {
        super(parameters);
    }

    public abstract WorkflowPojo listen();
}
