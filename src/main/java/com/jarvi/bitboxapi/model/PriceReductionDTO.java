package com.jarvi.bitboxapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class PriceReductionDTO {

    private long reducedPrice;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    public PriceReductionDTO(long reducedPrice, @NotNull Date startDate, @NotNull Date endDate) {
        this.reducedPrice = reducedPrice;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public PriceReductionDTO() {
    }

    public long getReducedPrice() {
        return reducedPrice;
    }

    public void setReducedPrice(long reducedPrice) {
        this.reducedPrice = reducedPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
