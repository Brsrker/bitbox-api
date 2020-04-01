package com.jarvi.bitboxapi.service;

import com.jarvi.bitboxapi.controller.exception.AlreadyExistException;
import com.jarvi.bitboxapi.model.ItemDTO;
import com.jarvi.bitboxapi.model.PriceReductionDTO;
import com.jarvi.bitboxapi.model.SupplierDTO;
import com.jarvi.bitboxapi.persistence.entity.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemServiceTest {

    private static SimpleDateFormat dateFormat;
    private static int itemCode;

    @Autowired
    private ItemService itemService;

    private ItemDTO itemDTO;



    @BeforeAll
    private static void beforeAll(){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        itemCode = getRandomNumberInRange(99, 999);
    }


    @Test
    void createItemFilledWhitSuppliersAndPriceReductions() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemCode(itemCode);
        itemDTO.setDescription("test item");
        itemDTO.setPrice(10000);

        Set<PriceReductionDTO> priceReductions = new HashSet<>();
        PriceReductionDTO priceReductionDTO = new PriceReductionDTO();
        priceReductions.add(new PriceReductionDTO(1, date("2020-01-01"), date("2020-01-10")));
        priceReductions.add(new PriceReductionDTO(1, date("2020-01-21"), date("2020-01-30")));
        priceReductions.add(new PriceReductionDTO(1, date("2020-01-11"), date("2020-01-20")));
        itemDTO.setPriceReductions(priceReductions);

        Set<SupplierDTO> suppliers = new HashSet<>();
        suppliers.add(new SupplierDTO("Provider 1", "ES"));
        suppliers.add(new SupplierDTO("Provider 2", "ES"));
        itemDTO.setSuppliers(suppliers);

        final Item item = itemService.create(itemDTO, "admin");

        assertNotNull(item);
    }

    @Test
    void createItemFilledWhitAlreadyExistException() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemCode(itemCode);
        itemDTO.setDescription("test item");

        Exception exception = assertThrows(AlreadyExistException.class, () -> {
            itemService.create(itemDTO, "admin");
        });

        assertTrue(exception.getMessage().contains("The Item " + itemCode+ " already exist!"));

    }

    private static Date date(String s) {
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
