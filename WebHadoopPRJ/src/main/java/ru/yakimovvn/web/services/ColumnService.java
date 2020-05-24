package ru.yakimovvn.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakimovvn.web.persistence.entities.Wfl_column;
import ru.yakimovvn.web.persistence.repositories.ColumnRepository;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;

    public Wfl_column save(Wfl_column column){
        return columnRepository.save(column);
    }


}
