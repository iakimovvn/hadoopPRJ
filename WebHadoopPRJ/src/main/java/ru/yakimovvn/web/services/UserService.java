package ru.yakimovvn.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yakimovvn.web.persistence.entities.Role;
import ru.yakimovvn.web.persistence.entities.User;
import ru.yakimovvn.web.persistence.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public User findByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public User getAnonymousUser() {
        return userRepository.findOneByLogin("anonymous");
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        if(roles == null)
            return new ArrayList<>();

        List<GrantedAuthority> grandAuthority = new ArrayList<>();

        for (Role role : roles) {
            grandAuthority.add((GrantedAuthority)role::getRole);
        }
        return grandAuthority;
    }



    public void delete(User user){
        userRepository.delete(user);
    }

    public boolean isUserExist(String login) {
        return userRepository.existsByLogin(login);
    }

    public User save(User user){
        return userRepository.save(user);
    }


    public String getHdfsFolderByLogin(String login){
        return userRepository.obtainHDFSFolderByLogin(login);
    }

    public String getLogFolderByLogin(String login){
        return userRepository.obtainLogFolderByLogin(login);
    }


}
