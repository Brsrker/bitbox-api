package com.jarvi.bitboxapi.model;

public class SupplierDTO {

    private String name;
    private String country;

    public SupplierDTO(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public SupplierDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
