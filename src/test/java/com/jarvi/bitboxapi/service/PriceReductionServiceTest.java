package com.jarvi.bitboxapi.service;

import com.jarvi.bitboxapi.model.PriceReductionDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PriceReductionServiceTest {

    private static SimpleDateFormat dateFormat;

    @Autowired
    private PriceReductionService priceReductionService;

    @BeforeAll
    private static void beforeAll(){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Test
    void dateIsNotOverlapping() {
        Set<PriceReductionDTO> priceReductions = new HashSet<>();
        priceReductions.add(new PriceReductionDTO(1, date("2020-01-01"), date("2020-01-10")));
        priceReductions.add(new PriceReductionDTO(1, date("2020-01-21"), date("2020-01-30")));
        priceReductions.add(new PriceReductionDTO(1, date("2020-01-11"), date("2020-01-20")));

        boolean isOverlapping = priceReductionService.dateIsOverlapping(priceReductions);

        assertFalse(isOverlapping);
    }

    @Test
    void dateIsOverlapping() {
        Set<PriceReductionDTO> priceReductions = new HashSet<>();
        priceReductions.add(new PriceReductionDTO(1, date("2020-01-01"), date("2020-01-15")));
        priceReductions.add(new PriceReductionDTO(1, date("2020-01-05"), date("2020-01-30")));
        priceReductions.add(new PriceReductionDTO(1, date("2020-01-11"), date("2020-01-20")));

        boolean isOverlapping = priceReductionService.dateIsOverlapping(priceReductions);

        assertTrue(isOverlapping);
    }

    private static Date date(String s) {
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

}
