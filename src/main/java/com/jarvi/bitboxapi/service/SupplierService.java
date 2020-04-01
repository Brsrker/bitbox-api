package com.jarvi.bitboxapi.service;

import com.jarvi.bitboxapi.model.SupplierDTO;
import com.jarvi.bitboxapi.persistence.entity.Supplier;
import com.jarvi.bitboxapi.persistence.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Transactional(rollbackFor = Exception.class)
    public Set<Supplier> addSuppliers(Set<SupplierDTO> suppliers) {
        Set<Supplier> itemSupplier = new HashSet<>();
        if (suppliers != null && suppliers.size() > 0) {
            for (SupplierDTO supplierDTO : suppliers) {
                if (supplierDTO != null) {
                    Supplier supplier = supplierRepository.findByName(supplierDTO.getName());
                    if (supplier == null){
                        supplier = Supplier.newSupplier().name(supplierDTO.getName()).country(supplierDTO.getCountry()).build();
                        supplierRepository.save(supplier);
                    }
                    itemSupplier.add(supplier);
                }
            }
        }
        return itemSupplier;
    }

    @Transactional(rollbackFor = Exception.class)
    public Iterable<Supplier> findByItemPriceReduced() {
        return supplierRepository.findByItemPriceReduced();
    }

}
