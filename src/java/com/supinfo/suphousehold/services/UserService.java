/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.services;

import com.supinfo.suphousehold.entities.User;
import java.util.Optional;

/**
 *
 * @author ericA
 */
public interface UserService {

    /**
     * Authentication service method
     * @param userName
     * @param password
     * @return 
     */
    Optional<User> authenticate(String userName, String password);
    
    User addUser(User user);
    Long countUsers();
    User saveUser(User user);
}
