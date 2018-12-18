/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author Dorian Maliszewski
 */
@Entity
@Table(name = "household_hou")
@Inheritance(strategy = InheritanceType.JOINED)
public class Household extends BaseEntity implements Serializable {
    private Double price;
    private String description;
    
    @Column(unique = true, nullable = false)
    private String reference;
    
    private String model;
    private Integer stock;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
