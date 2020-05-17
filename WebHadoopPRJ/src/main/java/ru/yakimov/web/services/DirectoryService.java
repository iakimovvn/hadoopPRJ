package ru.yakimov.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakimov.web.persistence.entities.Wfl_directory;
import ru.yakimov.web.persistence.repositories.DirectoryRepository;

import java.util.List;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
@RequiredArgsConstructor
public class DirectoryService {
    private final DirectoryRepository directoryRepository;

    public Wfl_directory save(Wfl_directory directory){
        return directoryRepository.save(directory);
    }

    public Iterable<Wfl_directory> saveAll(Iterable<Wfl_directory> directoryIterable){
        return directoryRepository.saveAll(directoryIterable);
    }
}
