package ru.yakimov.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakimov.web.persistence.entities.Wfl_config;
import ru.yakimov.web.persistence.repositories.ConfigRepository;

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
