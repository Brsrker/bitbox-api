package com.jarvi.bitboxapi.persistence.repository;

import com.jarvi.bitboxapi.persistence.entity.Role;
import com.jarvi.bitboxapi.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);


    Collection<Role> findByUsers(User user);
}
