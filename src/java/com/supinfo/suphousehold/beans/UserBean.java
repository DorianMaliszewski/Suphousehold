/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.beans;

import com.supinfo.suphousehold.entities.User;
import com.supinfo.suphousehold.services.UserService;
import java.io.Serializable;
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
@SessionScoped
@Stateful
@Named
public class UserBean implements Serializable {

    @EJB
    private UserService userService;

    private String username;
    private String password;;
    
    private User newUser = new User();

    private User connectedUser;

    public UserBean() {
    }

    public String login() {

        Optional<User> user = this.userService.authenticate(username, password);

        // If the authentication failed then return null.
        if (!user.isPresent()) {
            return null;
        }

        // Set the connected user
        this.connectedUser = user.get();

        return "/private/index.xhtml";
    }
    
    public String signup() {
        String redirectTo = "/public/subscribe.xhtml";
        FacesMessage facesMessage = null;
        try {
            User user = this.userService.addUser(newUser);
            newUser = new User();
            redirectTo = "/private/index.xhtml";
        } catch(Exception e) {
            e.printStackTrace();
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Une erreur est survenue lors de l'inscription", e.getMessage());
        }
        if (facesMessage != null) {
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
        return redirectTo;
    }
    
    public String logout() {
        this.connectedUser = null;
        return "/index.xhtml";
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

}
