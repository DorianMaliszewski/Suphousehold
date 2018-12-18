/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.services.impl;

import com.supinfo.suphousehold.dao.UserDao;
import com.supinfo.suphousehold.entities.User;
import com.supinfo.suphousehold.services.UserService;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ericA
 */
@Stateless
public class UserServiceImpl implements UserService {

    @EJB
    private UserDao userDao;

    @Override
    public Optional<User> authenticate(String userName, String password) {
        return this.userDao.authenticate(userName, password);
    }

    @Override
    public User addUser(User user) {
        return this.userDao.addUser(user);
    }

    @Override
    public Long countUsers() {
        return this.userDao.count();
    }

}
