package ru.yakimovvn.hadoop_worker.api;

import ru.yakimovvn.api.pojo.LogPojo;

import java.util.Map;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public abstract class LogWriter extends ComponentBase {

    public LogWriter(Map<String, Object> parameters) {
        super(parameters);
    }

    public abstract boolean write(LogPojo logPojo);
}
