/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.services.impl;

import com.supinfo.suphousehold.dao.HouseholdDao;
import com.supinfo.suphousehold.dao.PurchaseDao;
import com.supinfo.suphousehold.entities.Household;
import com.supinfo.suphousehold.entities.Purchase;
import com.supinfo.suphousehold.services.PurchaseService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Dorian Maliszewski
 */
@Stateless
public class PurchaseServiceImpl implements PurchaseService {
    @EJB
    private PurchaseDao purchaseDao;
    
    @EJB
    private HouseholdDao householdDao;
    

    @Override
    public List<Purchase> findMyPurchases(Long id) {
        return this.purchaseDao.findByUserId(id);
    }

    @Override
    public Purchase find(Long id) {
        return this.purchaseDao.findById(id);
    }

    @Override
    public List<Purchase> findAll() {
        return this.purchaseDao.findAll();
    }

    @Override
    public void save(Purchase p) {
        for(Household h: p.getItems()) {
            if (h.getStock() > 0) {
                h.setStock(h.getStock() - 1);
                this.householdDao.save(h);              
            } else {
                p.getItems().remove(h);
            }
        }
        this.purchaseDao.save(p);
    }
}
