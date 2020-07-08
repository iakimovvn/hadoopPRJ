package ru.yakimovvn.hadoop_worker.api;

import java.util.Map;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public abstract class WorkStrategy extends ComponentBase {

    protected Worker worker;

    protected WorkflowListener listener;

    protected LogWriter logWriter;

    protected ResultSender resultSender;

    public WorkStrategy(Map<String, Object> parameters) {
        super(parameters);
    }

    public void initialize(Worker worker, WorkflowListener listener, LogWriter logWriter, ResultSender resultSender) {
        this.worker = worker;
        this.listener = listener;
        this.logWriter = logWriter;
        this.resultSender = resultSender;
    }

    public abstract void workProcess();
}
