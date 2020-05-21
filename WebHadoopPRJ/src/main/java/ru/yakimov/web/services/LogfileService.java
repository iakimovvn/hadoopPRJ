package ru.yakimov.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakimov.web.persistence.entities.Wfl_logfile;
import ru.yakimov.web.persistence.entities.Workflow;
import ru.yakimov.web.persistence.repositories.LogfileRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
@RequiredArgsConstructor
public class LogfileService {

    private final LogfileRepository logfileRepository;

    public List<Wfl_logfile> findAllByWorkflow(Workflow workflow){
        return logfileRepository.findAllByWorkflow(workflow);
    }

    public Wfl_logfile createNewLogfile(Workflow workflow){
        Date date = new Date();
        Wfl_logfile newLog = Wfl_logfile.builder()
                .date(date)
                .file(date.getTime()+ ".log")
                .workflow(workflow)
                .writing(false)
                .build();
        return logfileRepository.save(newLog);
    }
}
