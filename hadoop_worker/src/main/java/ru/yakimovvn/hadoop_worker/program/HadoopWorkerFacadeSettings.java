package ru.yakimovvn.hadoop_worker.program;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import ru.yakimovvn.hadoop_worker.api.WorkflowListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public class HadoopWorkerFacadeSettings {

    public final String workerClass;

    public final Map<String, Object> workerConfiguration;

    public final String workflowListenerClass;

    public final Map<String, Object> workflowListenerConfiguration;

    public final String logWriterClass;

    public final Map<String, Object> logWriterConfiguration;

    public final String resultSenderClass;

    public final Map<String, Object> resultSenderConfiguration;

    public final String workStrategyClass;

    public final Map<String, Object> workStrategyConfiguration;

    public HadoopWorkerFacadeSettings(String workerClass, Map<String, Object> workerConfiguration, String workflowListenerClass, Map<String, Object> workflowListenerConfiguration, String logWriterClass, Map<String, Object> logWriterConfiguration, String resultSenderClass, Map<String, Object> resultSenderConfiguration, String workStrategyClass, Map<String, Object> workStrategyConfiguration) {
        this.workerClass = workerClass;
        this.workerConfiguration = workerConfiguration;
        this.workflowListenerClass = workflowListenerClass;
        this.workflowListenerConfiguration = workflowListenerConfiguration;
        this.logWriterClass = logWriterClass;
        this.logWriterConfiguration = logWriterConfiguration;
        this.resultSenderClass = resultSenderClass;
        this.resultSenderConfiguration = resultSenderConfiguration;
        this.workStrategyClass = workStrategyClass;
        this.workStrategyConfiguration = workStrategyConfiguration;
    }

    public static HadoopWorkerFacadeSettings read(File configFile) throws FileNotFoundException, YamlException {
        YamlConfig yamlConfig = new YamlConfig();
        yamlConfig.setAllowDuplicates(false); // default value is true

        YamlReader reader = new YamlReader(new FileReader(configFile.getAbsolutePath()));
        Map configMap = (Map)reader.read();


        Preconditions.checkState(configMap.containsKey("worker"), "Секция worker отсутствует в конфигурации %s", configFile.getAbsolutePath());

        Map workerMap = (Map)configMap.get("worker");

        Preconditions.checkState(workerMap.containsKey("class")
                && (workerMap.get("class") instanceof String)
                && !Strings.isNullOrEmpty((String)workerMap.get("class")), "Параметр /workerMap/class отсутствует в конфигурации %s", configFile.getAbsolutePath());

        String workerClass = (String)workerMap.get("class");


        Preconditions.checkState(configMap.containsKey("workflowListener"), "Секция workflowListener отсутствует в конфигурации %s", configFile.getAbsolutePath());

        Map workflowListenerMap = (Map)configMap.get("workflowListener");

        Preconditions.checkState(workflowListenerMap.containsKey("class")
                && (workflowListenerMap.get("class") instanceof String)
                && !Strings.isNullOrEmpty((String)workflowListenerMap.get("class")), "Параметр /workflowListener/class отсутствует в конфигурации %s", configFile.getAbsolutePath());

        String workflowListenerClass = (String)workflowListenerMap.get("class");


        Preconditions.checkState(configMap.containsKey("logWriter"), "Секция logWriter отсутствует в конфигурации %s", configFile.getAbsolutePath());

        Map logWriterMap = (Map)configMap.get("logWriter");

        Preconditions.checkState(logWriterMap.containsKey("class")
                && (logWriterMap.get("class") instanceof String)
                && !Strings.isNullOrEmpty((String)logWriterMap.get("class")), "Параметр /logWriter/class отсутствует в конфигурации %s", configFile.getAbsolutePath());

        String logWriterClass = (String)logWriterMap.get("class");


        Preconditions.checkState(configMap.containsKey("resultSender"), "Секция resultSender отсутствует в конфигурации %s", configFile.getAbsolutePath());

        Map resultSenderMap = (Map)configMap.get("resultSender");

        Preconditions.checkState(resultSenderMap.containsKey("class")
                && (resultSenderMap.get("class") instanceof String)
                && !Strings.isNullOrEmpty((String)resultSenderMap.get("class")), "Параметр /resultSender/class отсутствует в конфигурации %s", configFile.getAbsolutePath());

        String resultSenderClass = (String)resultSenderMap.get("class");


        Preconditions.checkState(configMap.containsKey("workStrategy"), "Секция workStrategy отсутствует в конфигурации %s", configFile.getAbsolutePath());

        Map workStrategyMap = (Map)configMap.get("workStrategy");

        Preconditions.checkState(workStrategyMap.containsKey("class")
                && (workStrategyMap.get("class") instanceof String)
                && !Strings.isNullOrEmpty((String)workStrategyMap.get("class")), "Параметр /workStrategy/class отсутствует в конфигурации %s", configFile.getAbsolutePath());

        String workStrategyClass = (String)workStrategyMap.get("class");


        return new HadoopWorkerFacadeSettings(
                workerClass,
                workerMap,
                workflowListenerClass,
                workflowListenerMap,
                logWriterClass,
                logWriterMap,
                resultSenderClass,
                resultSenderMap,
                workStrategyClass,
                workStrategyMap
        );
    }
}
