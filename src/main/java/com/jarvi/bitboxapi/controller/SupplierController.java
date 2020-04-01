package com.jarvi.bitboxapi.controller;

import com.jarvi.bitboxapi.controller.security.IsUser;
import com.jarvi.bitboxapi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @IsUser
    @GetMapping(value = "/supplier/itemPriceReduced")
    public ResponseEntity<?> findByItemPriceReduced(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(supplierService.findByItemPriceReduced());
    }

}
