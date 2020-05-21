package ru.yakimovvn.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakimovvn.web.persistence.entities.Wfl_config;
import ru.yakimovvn.web.persistence.repositories.ConfigRepository;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */
@RequiredArgsConstructor
@Service
public class ConfigService {
    private final ConfigRepository configRepository;

    public Wfl_config save(Wfl_config wfl_config){
        return configRepository.save(wfl_config);
    }
}
