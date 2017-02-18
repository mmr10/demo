package com.example.web;


import com.example.domain.User;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class UserController
{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Inject
    private UserService userService ;

    @RequestMapping(value = "/authenticate",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authentictae(@RequestParam("username") String username,
                                           @RequestParam("password") String password,HttpServletRequest request) throws URISyntaxException
    {
        logger.debug("Call rest to authenticate with user name : {}", username);

        Object result = userService.authenticate(username, password, request) ;
        return ResponseEntity.ok(result);

    }

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody User user) throws URISyntaxException
    {
        logger.debug("Call rest to create new  user  : {}", user);
        User result = userService.create(user) ;

        return ResponseEntity.created(new URI("/authenticate"))
                .body(result);

    }


    @RequestMapping(value = "/account",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getAccount()  throws  Exception{
        return Optional.ofNullable(userService.getUserWithAuthorities())
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }


}
