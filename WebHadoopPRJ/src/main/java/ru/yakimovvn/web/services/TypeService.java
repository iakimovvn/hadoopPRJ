package ru.yakimovvn.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yakimovvn.web.persistence.entities.Wfl_type;
import ru.yakimovvn.web.persistence.repositories.TypeRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    public List<Wfl_type> findAll(){
        return typeRepository.findAll();
    }

    public Wfl_type findByTitle(String title){
        return typeRepository.findFirstByTitle(title);
    }

    public List<Wfl_type> getAllWithOutOne(Wfl_type wfl_type){
        return typeRepository.findAll()
                .stream()
                .filter(v -> !v.equals(wfl_type))
                .collect(Collectors.toList());
    }


}
