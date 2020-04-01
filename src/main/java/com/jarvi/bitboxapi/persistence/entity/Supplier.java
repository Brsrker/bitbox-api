package com.jarvi.bitboxapi.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String country;
    @JsonIgnore
    @ManyToMany(mappedBy = "suppliers")
    private List<Item> items;

    private Supplier(Builder builder) {
        this.name = builder.name;
        this.country = builder.country;
        this.items = builder.items;
    }

    public static Builder newSupplier() {
        return new Builder();
    }

    public Supplier() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Supplier addItem(Item item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
        return this;
    }

    public Supplier removeItem(Item item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.remove(item);
        return this;
    }


    public static final class Builder {
        private String name;
        private String country;
        private List<Item> items;

        private Builder() {
        }

        public Supplier build() {
            return new Supplier(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder items(List<Item> items) {
            this.items = items;
            return this;
        }
    }
}
