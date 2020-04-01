package com.jarvi.bitboxapi.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "price_reductions")
public class PriceReduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    @Column
    private long reducedPrice;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    public PriceReduction() {
    }

    private PriceReduction(Builder builder) {
        this.item = builder.item;
        this.reducedPrice = builder.reducedPrice;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }

    public static Builder newPriceReduction() {
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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


    public static final class Builder {
        private Item item;
        private long reducedPrice;
        private Date startDate;
        private Date endDate;

        private Builder() {
        }

        public PriceReduction build() {
            return new PriceReduction(this);
        }

        public Builder item(Item item) {
            this.item = item;
            return this;
        }

        public Builder reducedPrice(long reducedPrice) {
            this.reducedPrice = reducedPrice;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }
    }
}
