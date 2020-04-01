package com.jarvi.bitboxapi.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class ItemDTO {

    @NotNull
    @Min(1)
    private int itemCode;
    @NotNull
    private String description;
    @Min(0)
    private long price;
    private Set<SupplierDTO> suppliers;
    private Set<PriceReductionDTO> priceReductions;

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Set<SupplierDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<SupplierDTO> suppliers) {
        this.suppliers = suppliers;
    }

    public Set<PriceReductionDTO> getPriceReductions() {
        return priceReductions;
    }

    public void setPriceReductions(Set<PriceReductionDTO> priceReductions) {
        this.priceReductions = priceReductions;
    }

}
