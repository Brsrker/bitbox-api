package com.jarvi.bitboxapi.persistence.repository;

import com.jarvi.bitboxapi.persistence.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select u from User u left join fetch u.roles r join fetch r.privileges where username = ?1")
    User findByUsername(String username);

    User findById(int id);
}
