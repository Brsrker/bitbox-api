package com.jarvi.bitboxapi.persistence.repository;

import com.jarvi.bitboxapi.persistence.entity.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {
    Privilege findByName(String name);
}
