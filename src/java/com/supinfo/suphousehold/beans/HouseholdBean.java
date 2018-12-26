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
 * Managed Bean JSf pour les vues concernant les pièces détachées
 * @author Dorian Maliszewski
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
    
    /**
     * Retourne la liste de toutes les pièces détachées de la base de données
     * @return List<Household> Toutes les pièces détachées
     */
    public List<Household> findAll() {
        return this.householdService.findAll();
    }
    
    /**
     * Permet de récupérer la pièces détachée dans la BDD possèdant l'id passsé en paramètre sinon redirige vers la liste des piècess détachées
     * @param id L'id de la pièce détachée a rechercher
     * @return  La vue du détail de la pièce
     */
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
    
    /**
     * Recherche les pièces détachées dans la base de donnée contenant la chaine de caractère passée en paramètre
     * @param s La chaîne a rechercher
     * @return La liste de pièces
     */
    public List<Household> searchItems(String s) {
        return this.householdService.search(s);
    }
    
    /**
     * Vérifie si le stock est supérieur à 0
     * @return Vrai ou Faux
     */
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
