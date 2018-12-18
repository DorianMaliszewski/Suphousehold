/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

/**
 *
 * @author Dorian Maliszewski
 */
@Entity
@Table(name = "users_usr")
@NamedQueries({
    @NamedQuery(name = "user.authenticate", query = "SELECT u FROM User u WHERE u.username = :userName AND u.password = :password")
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "usr_username")
    private String username;

    @Column(name = "usr_password")
    private String password;

    @Column(name = "usr_first_name")
    private String firstName;

    @Column(name = "usr_last_name")
    private String lastName;

    @Column(name = "usr_email")
    private String email;
    
    @OneToMany(targetEntity = Purchase.class, mappedBy = "createdBy")
    private ArrayList<Purchase> purchases = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Purchase> getPurchases () {
        return this.purchases;
    }

    public void setPurchases (ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }
    
    public String getFullName() {
        return this.lastName + " " + this.firstName;
    }
    
    @PreRemove
    public void preRemove () {
        for(Purchase p : purchases) {
            p.setCreatedBy(null);
        }
    }

}
