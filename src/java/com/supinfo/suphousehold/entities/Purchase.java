/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Dorian Maliszewski
 */
@Entity
@Table(name = "purchases_pur")
@NamedQueries({
    @NamedQuery(name = "purchase.findByUserId", query = "SELECT p FROM Purchase p WHERE p.createdBy = :userId")
})
public class Purchase extends BaseEntity implements Serializable {
    
    @ManyToOne(targetEntity = User.class)
    private User createdBy;

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    
}
