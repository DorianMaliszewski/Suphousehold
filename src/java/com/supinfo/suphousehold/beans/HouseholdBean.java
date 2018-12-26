/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.beans;

import com.supinfo.suphousehold.entities.Household;
import com.supinfo.suphousehold.services.HouseholdService;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author dorian
 */
@Stateless
@Named
@Dependent
public class HouseholdBean {
    @EJB
    private HouseholdService householdService;
    
    private List<Household> itemsFinded;
    private String searchString;
    public Household household;
    
    public List<Household> findAll() {
        return this.householdService.findAll();
    }
    
    public String showDetail(Long id) {
        Optional<Household> h = this.householdService.find(id);
        
        if (!h.isPresent()) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La pièce détachée n'existe pas", "");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "/public/households/index.xhtml?faces-redirect=true";
        }
        this.household = h.get();
        
        return "/public/households/details.xhtml?faces-redirect=true";
        
    }
    
    
    public List<Household> searchItems(String s) {
        return this.householdService.search(s);
    }
    
    public Boolean canAddToCart () {
        boolean can = true;
        if (this.household.getStock() <= 0) {
            can = false;
        }
        return can; 
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public List<Household> getItemsFinded() {
        return itemsFinded;
    }

    public void setItemsFinded(List<Household> itemsFinded) {
        this.itemsFinded = itemsFinded;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    
    
    
    
}
