package com.example.web;


import com.example.domain.Wish;
import com.example.security.AuthoritiesConstants;
import com.example.service.WishService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Api(basePath = "/", value = "wish", description = "Operations for  wish list", produces = "application/json")
@RestController
@RequestMapping("/api")
public class WishController
{

    public Logger logger = LoggerFactory.getLogger(WishController.class);

    @Inject
    private WishService wishService;



    @RequestMapping(value = "/wish",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Wish createWish(@RequestBody Wish wish)
            throws  URISyntaxException, IOException
    {

        return  wishService.createWish(wish);

    }

    @Secured({AuthoritiesConstants.USER,AuthoritiesConstants.ADMIN})
    @RequestMapping(value = "/wish",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Wish> getWishList() throws Exception
    {

        return wishService.getWishList();

    }
}
