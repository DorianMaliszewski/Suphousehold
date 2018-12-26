/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.services.impl;

import com.supinfo.suphousehold.dao.HouseholdDao;
import com.supinfo.suphousehold.entities.Household;
import com.supinfo.suphousehold.services.HouseholdService;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author dorian
 */
@Stateless
public class HouseholdServiceImpl implements HouseholdService {

    @EJB
    private HouseholdDao householdDao;
    
    @Override
    public List<Household> findAll() {
        return this.householdDao.findAll();
                
    }

    @Override
    public Optional<Household> find(Long id) {
        return this.householdDao.findById(id);
    }

    @Override
    public List<Household> search(String s) {
        return this.householdDao.descriptionContainString(s);
    }
    
}
