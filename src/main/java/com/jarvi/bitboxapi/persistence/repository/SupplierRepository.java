package com.jarvi.bitboxapi.persistence.repository;

import com.jarvi.bitboxapi.persistence.entity.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Integer> {
    Supplier findByName(String name);

    /**
     * List of suppliers associated to items whose price has been reduced.
     * @return
     */
    @Query("select s from Supplier s left join fetch s.items i left join i.priceReductions p where i.priceReductions is not empty order by s.name")
    Set<Supplier> findByItemPriceReduced();
}
