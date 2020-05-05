package ru.yakimov.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yakimov.web.persistence.entities.Wfl_user;
import ru.yakimov.web.persistence.entities.enums.Role;
import ru.yakimov.web.persistence.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public Wfl_user findByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public Wfl_user getAnonymousUser() {
        return userRepository.findOneByLogin("anonymous");
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Wfl_user user = userRepository.findOneByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new User(user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
        return role != null ? new ArrayList<GrantedAuthority>() {{ add((GrantedAuthority) role::name); }} : new ArrayList<>();
    }


    public Wfl_user save(Wfl_user user){
        return userRepository.save(user);
    }

    public void delete(Wfl_user user){
        userRepository.delete(user);
    }

    public boolean isUserExist(String login) {
        return userRepository.existsByLogin(login);
    }

}
