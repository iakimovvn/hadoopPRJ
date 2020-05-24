package ru.yakimovvn.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakimovvn.web.persistence.entities.Wfl_directory_from;
import ru.yakimovvn.web.persistence.repositories.DirectoryRepository;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
@RequiredArgsConstructor
public class DirectoryService {
    private final DirectoryRepository directoryRepository;

    public Wfl_directory_from save(Wfl_directory_from directory){
        return directoryRepository.save(directory);
    }

    public Iterable<Wfl_directory_from> saveAll(Iterable<Wfl_directory_from> directoryIterable){
        return directoryRepository.saveAll(directoryIterable);
    }
}
