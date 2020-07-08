package ru.yakimovvn.hadoop_worker.api;

import ru.yakimovvn.api.pojo.WorkflowPojo;

import java.util.Map;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public abstract class Worker extends ComponentBase {

    public Worker(Map<String, Object> parameters) {
        super(parameters);
    }

    public abstract boolean work (WorkflowPojo workflow);
}
