package com.example.service;


import com.example.domain.Wish;
import com.example.repository.WishRepository;
import com.example.repository.elastic.WishSearchRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WishService
{


    @Inject
    private WishRepository wishRepository;

    @Inject
    private WishSearchRepository wishElasticRespository;

    @Inject
    private ApplicationEventPublisher publisher;

    public Wish createWish(Wish wish) throws IOException
    {

        wish.setCreationDate(new Date());
        wishRepository.save(wish);
        wishElasticRespository.save(wish);
        //this.publisher.publishEvent(new ReactEvent(this, converter.fromWishToBusiness(wish)));
        return wish;
    }


    public List<Wish> getWishList() throws Exception
    {

        return
                StreamSupport.stream(wishElasticRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "creationDate"))).spliterator(), false)
                        .map(wish -> wish)
                        .collect(Collectors.toList());
    }
}
