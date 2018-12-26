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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Managed Bean JSF utilisé pour les vues concernant les achats effectués
 * @author Dorian Maliszewski
 */
@Named
@Stateless
public class PurchaseBean {
    
    private Purchase purchase;
    
    @EJB
    private PurchaseService purchaseService;
    
    @Inject
    @SessionScoped
    private UserBean userBean;
    
    /**
     * Retourne la liste de toutes les commandes de l'utilisateur connecté
     * @return List<Purchase> La liste des commandes
     */
    public List<Purchase> getMyPurchases() {
        List<Purchase> purchases = this.purchaseService.findMyPurchases(userBean.getConnectedUser().getId());
        return purchases != null ? purchases : new ArrayList<>();
    }
    
    /**
     * Recupère la commande possèdant l'id passé en parmaètre dans la BDD et affiche ke détail de la commande 
     * Si l'id n'existe pas redirige vers la page d'accueil
     * @param id l'id à rechercher
     * @return 
     */
    public String showDetail(Long id) {
        this.purchase = this.purchaseService.find(id);
        if (this.purchase == null) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La commande n'existe pas", "Entity Not found");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "/index.xhtml?faces-redirect=true";
        }
        return "/private/purchases/detail.xhtml?id=" + id;
    }
    
    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
