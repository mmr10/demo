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
    private AuthenticationManager authenticationManager;

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
        return ResponseEntity.created(new URI("/authenticate"))
                .body(result);

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

    private Optional<User> getCurrentUser()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Optional<User> user = null;
        if (authentication != null)
        {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            user = Optional.ofNullable((User) springSecurityUser);
        }
        return user;
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

    @RequestMapping(value = "/admin",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> adminTestSecurity()  throws  Exception{
        return new ResponseEntity<String>("if you see this result, it's mean you are identified as ADMIN", HttpStatus.OK);
    }

    @RequestMapping(value = "/user",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<String> userTestSecurity()  throws  Exception{
        return new ResponseEntity<String>("if you see this result, it's mean you are identified as simple user or admin", HttpStatus.OK);

    }
}
