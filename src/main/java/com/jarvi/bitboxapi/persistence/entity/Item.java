package com.jarvi.bitboxapi.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item {

    private Item(Builder builder) {
        this.itemCode = builder.itemCode;
        this.description = builder.description;
        this.price = builder.price;
        this.state = builder.state;
        this.suppliers = builder.suppliers;
        this.creationDate = builder.creationDate;
        this.creator = builder.creator;
        this.discontinuedAt = builder.discontinuedAt;
        this.discontinuedBy = builder.discontinuedBy;
        this.discontinuedReason = builder.discontinuedReason;
    }

    public static Builder newItem() {
        return new Builder();
    }

    public enum State {
        ACTIVE, DISCONTINUED
    }

    @Id
    private int itemCode;
    @Column
    private String description;
    @Column
    private long price;
    @Enumerated(EnumType.STRING)
    @Column(length = 12, columnDefinition = "varchar(12) default 'ACTIVE'")
    private State state;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "item_supplier",
            joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "itemCode"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id", referencedColumnName = "id"))
    private Set<Supplier> suppliers;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Set<PriceReduction> priceReductions;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @ManyToOne
    @JoinColumn(name="creator")
    private User creator;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date discontinuedAt;
    @ManyToOne
    @JoinColumn(name="discontinuedBy")
    private User discontinuedBy;
    @Column
    private String discontinuedReason;

    public Item() {
    }

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public Set<PriceReduction> getPriceReductions() {
        return priceReductions;
    }

    public void setPriceReductions(Set<PriceReduction> priceReductions) {
        this.priceReductions = priceReductions;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getDiscontinuedAt() {
        return discontinuedAt;
    }

    public void setDiscontinuedAt(Date discontinuedAt) {
        this.discontinuedAt = discontinuedAt;
    }

    public User getDiscontinuedBy() {
        return discontinuedBy;
    }

    public void setDiscontinuedBy(User discontinuedBy) {
        this.discontinuedBy = discontinuedBy;
    }

    public String getDiscontinuedReason() {
        return discontinuedReason;
    }

    public void setDiscontinuedReason(String discontinuedReason) {
        this.discontinuedReason = discontinuedReason;
    }

    public void addSuppliers(Supplier supplier) {
        if (this.suppliers == null) {
            this.suppliers = new HashSet<>();
        }
        this.suppliers.add(supplier);
    }

    public void removeSuppliers(Supplier supplier) {
        if (this.suppliers == null) {
            this.suppliers = new HashSet<>();
        }
        this.suppliers.remove(supplier);
    }

    public void addPriceReductions(PriceReduction priceReductions) {
        if (this.priceReductions == null) {
            this.priceReductions = new HashSet<>();
        }
        this.priceReductions.add(priceReductions);
    }

    public void removePriceReductions(PriceReduction priceReductions) {
        if (this.priceReductions == null) {
            this.priceReductions = new HashSet<>();
        }
        this.priceReductions.remove(priceReductions);
    }

    public static final class Builder {
        private int itemCode;
        private String description;
        private long price;
        private State state;
        private Set<Supplier> suppliers;
        private Set<PriceReduction> priceReductions;
        private Date creationDate;
        private User creator;
        private Date discontinuedAt;
        private User discontinuedBy;
        private String discontinuedReason;

        private Builder() {
        }


        public Item build() {
            return new Item(this);
        }

        public Builder itemCode(int itemCode) {
            this.itemCode = itemCode;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(long price) {
            this.price = price;
            return this;
        }

        public Builder state(State state) {
            this.state = state;
            return this;
        }

        public Builder suppliers(Set<Supplier> suppliers) {
            this.suppliers = suppliers;
            return this;
        }

        public Builder priceReduction(Set<PriceReduction> priceReductions) {
            this.priceReductions = priceReductions;
            return this;
        }

        public Builder creationDate(Date creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder creator(User creator) {
            this.creator = creator;
            return this;
        }

        public Builder discontinuedAt(Date discontinuedAt) {
            this.discontinuedAt = discontinuedAt;
            return this;
        }

        public Builder discontinuedBy(User discontinuedBy) {
            this.discontinuedBy = discontinuedBy;
            return this;
        }

        public Builder discontinuedReason(String discontinuedReason) {
            this.discontinuedReason = discontinuedReason;
            return this;
        }
    }
}
