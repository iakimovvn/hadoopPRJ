package ru.yakimovvn.hadoop_worker.program;

import com.esotericsoftware.yamlbeans.YamlException;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yakimovvn.hadoop_worker.api.*;
import ru.yakimovvn.hadoop_worker.util.ClassLoaderUtil;
import ru.yakimovvn.hadoop_worker.util.ComponentUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public class HadoopWorkerFacade {

    private static Logger logger;

    private final File configFile;

    private HadoopWorkerFacadeSettings settings;


    public HadoopWorkerFacade(File configFile) {
        this.configFile = configFile;
    }

    public void initialize() throws FileNotFoundException, YamlException {
        settings = HadoopWorkerFacadeSettings.read(configFile);
    }

    public Worker createWorker() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (Worker) ComponentUtil.createInstance(settings.workerClass, settings.workerConfiguration);
    }

    public WorkflowListener createWorkflowListener() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (WorkflowListener) ComponentUtil.createInstance(settings.workflowListenerClass, settings.workflowListenerConfiguration);
    }

    public LogWriter createLogWriter() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (LogWriter) ComponentUtil.createInstance(settings.logWriterClass, settings.logWriterConfiguration);
    }

    public ResultSender createResultSender() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (ResultSender) ComponentUtil.createInstance(settings.resultSenderClass, settings.resultSenderConfiguration);
    }

    public WorkStrategy createWorkStrategy() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (WorkStrategy) ComponentUtil.createInstance(settings.workStrategyClass, settings.workStrategyConfiguration);
    }


    public static void main(String[] args) throws Exception {

        //Добавить VM options: -Dcom.sun.management.jmxremote
        System.setProperty("com.sun.management.jmxremote.ssl", "false");
        System.setProperty("com.sun.management.jmxremote.port", "1099");
        System.setProperty("com.sun.management.jmxremote.local.only", "false");
        System.setProperty("com.sun.management.jmxremote.authenticate", "false");
        //System.setProperty("java.rmi.server.hostname", "127.0.1.1");

        // Путь к папке, в которой находится приложение
        String path = ClassLoaderUtil.GetApplicationRootPath(HadoopWorkerFacade.class);

        // Установим системное свойство пути приложения
        System.getProperties().setProperty("indexer.home", path);
        System.getProperties().setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        // Пути к файлам конфигурации
        File logConfigPath = new File(path, "config/log4j.properties");

        System.setProperty("log4j.configurationFile", logConfigPath.getAbsolutePath());

        logger = LoggerFactory.getLogger(HadoopWorkerFacade.class);
        logger.info("root: " + path);

        Preconditions.checkArgument(args.length >=2);

        Preconditions.checkArgument(args[0].equals("-config"));

        String configFileStr = args[1];

        File configFile = new File(configFileStr);

        HadoopWorkerFacade facade = new HadoopWorkerFacade(configFile);

        facade.initialize();

        Worker worker = facade.createWorker();
        WorkflowListener listener = facade.createWorkflowListener();
        LogWriter logWriter = facade.createLogWriter();
        ResultSender resultSender = facade.createResultSender();
        WorkStrategy strategy = facade.createWorkStrategy();

        strategy.initialize(worker,listener, logWriter, resultSender);

        strategy.workProcess();

    }

}
