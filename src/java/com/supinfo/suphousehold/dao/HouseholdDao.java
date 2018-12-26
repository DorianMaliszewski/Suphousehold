/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.dao;

import com.supinfo.suphousehold.entities.Household;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author dorian
 */
public interface HouseholdDao {
    public List<Household> findAll();

    public Optional<Household> findById(Long id);
    
    public void save(Household h);

    public List<Household> descriptionContainString(String s);
}
