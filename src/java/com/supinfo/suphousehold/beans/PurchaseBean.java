/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.beans;

import com.supinfo.suphousehold.entities.Purchase;
import com.supinfo.suphousehold.services.PurchaseService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Dorian Maliszewski
 */
@Named
@Stateless
public class PurchaseBean {
    
    private String search;
    private Purchase purchase;
    
    @EJB
    private PurchaseService purchaseService;
    
    @Inject
    @SessionScoped
    private UserBean userBean;
    
    public List<Purchase> getMyPurchases() {
        List<Purchase> purchases = this.purchaseService.findMyPurchases(userBean.getConnectedUser().getId());
        return purchases != null ? purchases : new ArrayList<>();
    }
    
    public List<Purchase> findAll() {
        return this.purchaseService.findAll();
    }
    
    public String showDetail(Long id) {
        this.purchase = this.purchaseService.find(id);
        if (this.purchase == null) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La commande n'existe pas", "Entity Not found");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "/index.xhtml?faces-redirect=true";
        }
        return "/private/purchases/detail.xhtml?id=" + id;
    }
    
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
