/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.dao.jpa;

import com.supinfo.suphousehold.dao.UserDao;
import com.supinfo.suphousehold.entities.User;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Dorian Maliszewski
 */
@Stateless
public class JpaUserDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> authenticate(String userName, String password) {
        User response;
        try {
            // Get the authenticated user using the named query.
            response = (User) em.createNamedQuery("user.authenticate")
                    .setParameter("userName", userName)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException nre) {
            // If there is no result then return an empty optional.
            return Optional.empty();
        }

        return Optional.of(response);
    }

    @Override
    public Long count() {
        CriteriaQuery<Long> q = this.em.getCriteriaBuilder().createQuery(Long.class);
        q.select(this.em.getCriteriaBuilder().count(q.from(User.class)));
        return this.em.createQuery(q).getSingleResult();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
        return user;
    }
    
    

}
