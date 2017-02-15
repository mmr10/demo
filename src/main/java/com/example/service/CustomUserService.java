package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
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

    private static Logger logger = LoggerFactory.getLogger(CustomUserService.class);

    @Inject
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {

        logger.info("Authenticating the user {}", login);
        Optional<User> userFound = userRepository.findOneByUsername(login);
        return userFound.map(user -> {
            if (!user.isEnabled())
            {
                throw new UserNotActivatedException("User " + login + " has not been activated yet");
            }
            return user;
        }).orElseThrow(() -> new UsernameNotFoundException("user " + login + " Not found in the database"));
    }


}
