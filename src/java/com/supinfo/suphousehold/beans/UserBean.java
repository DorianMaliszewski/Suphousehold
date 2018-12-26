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
 * Managed Bean Stateful pour gérer toutes les action d'un client sur l'application
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

    /**
     * Connecte l'utilisateur
     * @return Retourne vers une page confirmantr la connexion sinon redirige vers la page de connexion avec l'erreur soulevée
     */
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

    /**
     * Inscrit un nouvel utilisateur en l'ajoutant dans la bdd et le connecte
     * @return Retourne sur la page de confirmation de connexion si tous c'est bien passé sinon redirige sur la page d'inscription
     */
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

    /**
     * Déconnecte l'utilisateur
     * @return Retourne vers la page d'accueil
     */
    public String logout() {
        this.connectedUser = null;
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * Permet de modifier le mot de passe de l'utilisateur connecte
     * @return Retourne la page de confirmation de connexion si ok sinon retourne vers la page de changement de mot de passe en affichant les problemes
     */
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
    /**
     * Ajoute un article dans le panier
     * @param household L'article a ajouter
     * @return La vue ou se trouve la liste des pièces
     */
    public String addToCart (Household household) {
        this.panier.add(household);
        return "/public/households/index.xhtml";
    }
    
    /**
     * Retire un article du panier
     * @param household L'articler a retirer
     * @return Redirige vers la vue du panier
     */
    public String removeToCart (Household household) {
        this.panier.remove(household);
        return "/private/my-cart.xhtml";
    }
    
    /**
     * Rechercher les pièces détachée dans la BDD possèdant dans leur description la chaine de caractere de l'attribut "search"
     * @return Redirige vers la vue d'index avec la liste alimenté
     */
    public String search() {
        this.itemsFinded = this.householdService.search(this.searchText);
        return "/faces/index.xhtml?faces-redirect=true";
    }
    
    /**
     * Renvoi le total Ht du panier
     * @return Total HT
     */
    public Double getTotalHT() {
        Double ht = 0D;
        for(Household h: this.panier) {
            ht += h.getPrice();
        }
        return ht;
    }
    
    /**
     * renvoi le total TTC avec une TVA a 20%
     * @return Total TTC
     */
    public Double getTotalTTC() {
        return this.getTotalHT() * 1.20;
    }
    
    /**
     * Enregistre l'achat du panier par l'utilisateur
     * @return Redirige vers la vue ou se trouve toutes ses commandes
     */
    public String buy() {
        Purchase p = new Purchase();
        p.setItems(this.panier);
        p.setCreatedBy(this.connectedUser);
        p.setTotal(this.getTotalTTC());
        this.purchaseService.save(p);
        this.panier = new ArrayList<>();
        return "/private/my-purchases.xhtml";
    }

    /**
     * Récupère le nombres d'utilisateur dans la bdd
     * @return 
     */
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
