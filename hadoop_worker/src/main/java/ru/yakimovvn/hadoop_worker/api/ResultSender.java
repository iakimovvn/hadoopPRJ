package ru.yakimovvn.hadoop_worker.api;

import java.util.Map;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public abstract class ResultSender extends ComponentBase {

    public ResultSender(Map<String, Object> parameters) {
        super(parameters);
    }

    public abstract boolean sendResult(String result);
}
