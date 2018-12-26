/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.dao;

import com.supinfo.suphousehold.entities.Purchase;
import java.util.List;

/**
 *
 * @author Dorian Maliszewski
 */
public interface PurchaseDao {

    List<Purchase> findByUserId(Long id);

    public Purchase findById(Long id);

    public List<Purchase> findAll();

    public void save(Purchase p);
    
}
