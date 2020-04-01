package com.jarvi.bitboxapi.persistence.repository;

import com.jarvi.bitboxapi.persistence.entity.Item;
import com.jarvi.bitboxapi.persistence.entity.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
    Item findByItemCode(int itemCode);

    /**
     * List of cheapest item per supplier.
     * @param supplier
     * @return
     */
    @Query("select i from Item i left join i.suppliers s where s = ?1 order by i.price asc")
    List<Item> findByCheapestPerSupplier(Supplier supplier);
}
