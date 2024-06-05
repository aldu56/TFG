package com.example.API_biblioteca_multimedia.model.repository;

import com.example.API_biblioteca_multimedia.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends CrudRepository<Role, Long>
{
    Role findByName(String name);
}
