package com.example.service;

import com.example.domain.User;
import com.example.repositoryJpa.UserRepository;
import com.example.security.UserNotActivatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;


@Service("customUserService")
public class CustomUserService implements UserDetailsService
{

    @Inject
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {

        Optional<User> userBase = userRepository.findOneByUsername(login);
        return userBase.map(user -> {
            if (!user.isEnabled())
            {
                throw new UserNotActivatedException("User not active");
            }
            return user;
        }).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }


}
