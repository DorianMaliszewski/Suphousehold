/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.services;

import java.util.List;
import com.supinfo.suphousehold.entities.Purchase;
/**
 *
 * @author Dorian Maliszewski
 */
public interface PurchaseService {
    List<Purchase> findMyPurchases(Long id);
    List<Purchase> findAll();
    Purchase find(Long id);

    public void save(Purchase p);
}
