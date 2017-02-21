package com.example.repositoryJpa;

import com.example.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Long>
{

    Optional<Role> findOneByName(String name);
}
