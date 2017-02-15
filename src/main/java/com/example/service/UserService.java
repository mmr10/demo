package com.example.service;


import com.example.domain.Authority;
import com.example.domain.User;
import com.example.repository.AuthorityRepository;
import com.example.repository.UserRepository;
import com.example.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService
{

    private static Logger logger = LoggerFactory.getLogger(CustomUserService.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private RestTemplate restTemplate ;

    public User create(User user)
    {

        userRepository.findOneByUsername(user.getUsername())
                .ifPresent(us -> new Exception("the user name" + us.getUsername() + " is already in user"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Authority> authority = authorityRepository.findOneByName("ROLE_USER");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority.get());
        userRepository.save(user);
        return user;

    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() throws  Exception{
        return userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin()).filter(User::isEnabled)
                .map(us -> {
                    us.getAuthorities().size();
                    return us;
                }).orElseThrow(() -> new Exception("user not found ")) ;

    }

    public  Object authenticate(String userName,String password,HttpServletRequest request) {

        String baseUrl = request.getScheme() + // "http"
                "://" +                                // "://"
                request.getServerName() +              // "myhost"
                ":" +                                  // ":"
                request.getServerPort() +              // "80"
                request.getContextPath();              // "/myContextPath" or "" if deployed in root context

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("client_secret", "demo_secret");
        map.add("client_id", "demo_id");
        map.add("grant_type", "password");
        map.add("scope", "read");
        map.add("username", userName);
        map.add("password", password);

        Object result = restTemplate.postForObject(baseUrl+"/oauth/token", map, Object.class);
        return result ;
    }
}
