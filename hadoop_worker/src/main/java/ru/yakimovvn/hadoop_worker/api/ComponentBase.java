package ru.yakimovvn.hadoop_worker.api;

import java.util.Map;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public abstract class ComponentBase {

    protected final Map<String, Object> parameters;

    public ComponentBase(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
