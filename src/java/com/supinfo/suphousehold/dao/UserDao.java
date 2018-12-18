/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.dao;

import com.supinfo.suphousehold.entities.User;
import java.util.Optional;

/**
 *
 * @author Dorian Maliszewski
 */
public interface UserDao {

    /**
     * This method is used to authenticate a user
     *
     * @param userName of type {@link String} represents the username trying to login
     * @param password of type {@link String} represents the password
     * @return An entity of type {@link User} if the authentication succeeded;
     * null if not.
     */
    Optional<User> authenticate(String userName, String password);
    
    User addUser(User user);
    Long count();
}
