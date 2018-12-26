/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.dao.jpa;

import com.supinfo.suphousehold.dao.HouseholdDao;
import com.supinfo.suphousehold.entities.Household;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dorian
 */
@Stateless
public class JpaHouseholdDao implements HouseholdDao {
     
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Household> findAll() {
        return this.em.createQuery("Select h from Household h").getResultList();
    }

    @Override
    public Optional<Household> findById(Long id) {
        Household h = null;
        try {
         h = this.em.find(Household.class, id);
         
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(h);
    }

    @Override
    public void save(Household h) {
        if (h.getId() != null) {
            this.em.merge(h);
        } else {
            this.em.persist(h);
        }
    }

    @Override
    public List<Household> descriptionContainString(String s) {
        return this.em.createNamedQuery("household.findAllByDescriptionContains").setParameter("searchString", "%" + s + "%").getResultList();
    }
    
}
