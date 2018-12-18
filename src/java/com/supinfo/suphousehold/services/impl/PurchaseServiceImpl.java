/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.services.impl;

import com.supinfo.suphousehold.dao.PurchaseDao;
import com.supinfo.suphousehold.entities.Purchase;
import com.supinfo.suphousehold.beans.UserBean;
import com.supinfo.suphousehold.services.PurchaseService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author dorian
 */
@Stateless
public class PurchaseServiceImpl implements PurchaseService {
    @EJB
    private PurchaseDao purchaseDao;
    

    @Override
    public List<Purchase> findMyPurchases(Long id) {
        return this.purchaseDao.findByUserId(id);
    }
    
    
}
