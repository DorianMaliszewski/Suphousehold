/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.beans;

import com.supinfo.suphousehold.entities.Household;
import com.supinfo.suphousehold.entities.Purchase;
import com.supinfo.suphousehold.entities.User;
import com.supinfo.suphousehold.services.HouseholdService;
import com.supinfo.suphousehold.services.PurchaseService;
import com.supinfo.suphousehold.services.UserService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Dorian Maliszewski
 */
@Stateful
@SessionScoped
@Named
public class UserBean implements Serializable {

    @EJB
    private UserService userService;
    
    @EJB
    private PurchaseService purchaseService;
    
    @EJB
    private HouseholdService householdService;

    private String username;
    private String password;
    private String newPassword;
    private String confirmPassword;

    private User newUser = new User();
    private ArrayList<Household> panier = new ArrayList();
    private User connectedUser;
    private String searchText;
    private List<Household> itemsFinded;
    

    public UserBean() {
    }

    public String login() {

        Optional<User> user = this.userService.authenticate(username, password);

        // If the authentication failed then return null.
        if (!user.isPresent()) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Identifiants incorrect", "");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return null;
        }
        // Set the connected user
        this.connectedUser = user.get();
        this.password = null;
        return "/private/index.xhtml?faces-redirect=true";
    }

    public String signup() {
        String redirectTo = "/public/subscribe.xhtml";
        FacesMessage facesMessage = null;
        try {
            User user = this.userService.addUser(newUser);
            newUser = new User();
            this.connectedUser = user;
            redirectTo = "/private/index.xhtml";
        } catch (Exception e) {
            e.printStackTrace();
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur est survenue lors de l'inscription", e.getMessage());
        }
        if (facesMessage != null) {
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
        return redirectTo + "?faces-redirect=true";
    }

    public String logout() {
        this.connectedUser = null;
        return "/index.xhtml?faces-redirect=true";
    }

    public String changePassword() {
        FacesMessage facesMessage = null;
        String redirectTo = "";
        if (!this.password.equals(this.connectedUser.getPassword())) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le mot de passe actuel est incorrect", "");
        } else if (!this.newPassword.equals(this.confirmPassword)) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La confirmation de mot de passe est différente du nouveau mot de passe", "");
        } else {
            redirectTo = "/private/index.xhtml?faces-redirect=true";
            try {
                this.connectedUser.setPassword(newPassword);
                this.connectedUser = this.userService.saveUser(this.connectedUser);
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Le mot de passe à été modifié", "");
                this.confirmPassword = null;
                this.newPassword = null;
                this.password = null;
            } catch (Exception e) {
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur est survenue lors de l'opération", "");
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return redirectTo;
    }
    
    public String addToCart (Household household) {
        this.panier.add(household);
        return "/public/households/index.xhtml";
    }
    
    public String removeToCart (Household household) {
        this.panier.remove(household);
        return "/private/my-cart.xhtml";
    }
    
    public String search() {
        this.itemsFinded = this.householdService.search(this.searchText);
        return "/faces/index.xhtml?faces-redirect=true";
    }
    
    public Double getTotalHT() {
        Double ht = 0D;
        for(Household h: this.panier) {
            ht += h.getPrice();
        }
        return ht;
    }
    
    public Double getTotalTTC() {
        return this.getTotalHT() * 1.20;
    }
    
    public String buy() {
        Purchase p = new Purchase();
        p.setItems(this.panier);
        p.setCreatedBy(this.connectedUser);
        p.setTotal(this.getTotalTTC());
        this.purchaseService.save(p);
        this.panier = new ArrayList<>();
        return "/private/my-purchases.xhtml";
    }

    public Long getNbUsers() {
        return this.userService.countUsers();
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

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public ArrayList<Household> getPanier() {
        return panier;
    }

    public void setPanier(ArrayList<Household> panier) {
        this.panier = panier;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<Household> getItemsFinded() {
        return itemsFinded;
    }

    public void setItemsFinded(List<Household> itemsFinded) {
        this.itemsFinded = itemsFinded;
    }
    
    
    
}
