/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.beans;

import com.supinfo.suphousehold.dao.PurchaseDao;
import com.supinfo.suphousehold.entities.Purchase;
import com.supinfo.suphousehold.services.PurchaseService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;
import javax.inject.Scope;

/**
 *
 * @author dorian
 */
@Named
@Stateless
public class PurchaseBean {
    
    @EJB
    private PurchaseService purchaseService;
    
    @EJB
    private UserBean userBean;
    
    public List<Purchase> getMyPurchases() {
        List<Purchase> purchases = this.purchaseService.findMyPurchases(userBean.getConnectedUser().getId());
        return purchases;
    }
}
