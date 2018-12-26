/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.dao.jpa;

import com.supinfo.suphousehold.dao.PurchaseDao;
import com.supinfo.suphousehold.entities.Purchase;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dorian Maliszewski
 */
@Stateless
public class JpaPurchaseDao implements PurchaseDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Purchase> findByUserId(Long id) {
        return this.em.createNamedQuery("purchase.findByUserId").setParameter("userId", id).getResultList();
    }

    @Override
    public Purchase findById(Long id) {
        return this.em.find(Purchase.class, id);
        
    }

    @Override
    public List<Purchase> findAll() {
        return this.em.createQuery("Select p from Purchase p").getResultList();
    }

    @Override
    public void save(Purchase p) {
        this.em.persist(p);
    }
    
}
