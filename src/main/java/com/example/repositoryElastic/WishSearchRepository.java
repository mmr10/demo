package com.example.repositoryElastic;

import com.example.domain.Wish;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface WishSearchRepository extends ElasticsearchRepository<Wish, Long>
{

}
