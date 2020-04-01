package com.jarvi.bitboxapi.service;

import com.jarvi.bitboxapi.controller.exception.InvalidDataException;
import com.jarvi.bitboxapi.model.PriceReductionDTO;
import com.jarvi.bitboxapi.persistence.entity.PriceReduction;
import com.jarvi.bitboxapi.persistence.repository.PriceReductionRepository;
import com.jarvi.bitboxapi.util.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PriceReductionService {

    @Autowired
    PriceReductionRepository priceReductionRepository;

    @Transactional(rollbackFor = Exception.class)
    public Set<PriceReduction> addPriceReductions(Set<PriceReductionDTO> priceReductions) {
        Set<PriceReduction> itemPriceReduction = new HashSet<>();
        if (priceReductions != null && priceReductions.size() > 0) {
            if (!dateIsOverlapping(priceReductions)) {
                for (PriceReductionDTO priceReductionDTO : priceReductions) {
                    if (priceReductionDTO != null) {
                        PriceReduction priceReduction = PriceReduction.newPriceReduction()
                                .reducedPrice(priceReductionDTO.getReducedPrice())
                                .startDate(priceReductionDTO.getStartDate())
                                .endDate(priceReductionDTO.getEndDate())
                                .build();
                        priceReductionRepository.save(priceReduction);
                        itemPriceReduction.add(priceReduction);
                    }
                }
            } else {
                throw new InvalidDataException("The price reduction is overlapping dates!");
            }
        }
        return itemPriceReduction;

    }

    /**
     * Control of overlapping dates when inserting a price reduction.
     * @param priceReductions
     * @return
     */
    public boolean dateIsOverlapping(Set<PriceReductionDTO> priceReductions) {
        List<Period> timeLine = new ArrayList<>();
        for (PriceReductionDTO priceReduction: priceReductions) {
            if (priceReduction != null){
                for (Period period: timeLine) {
                    boolean startIsOverlapping = period.in(priceReduction.getStartDate());
                    boolean endIsOverlapping = period.in(priceReduction.getEndDate());
                    if(startIsOverlapping || endIsOverlapping){
                        return true;
                    }
                }
                timeLine.add(new Period(priceReduction.getStartDate(), priceReduction.getEndDate()));
            }
        }
        return false;
    }



}
