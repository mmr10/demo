package com.example.repository;


import com.example.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WishRepository extends JpaRepository<Wish,Long>
{

}
