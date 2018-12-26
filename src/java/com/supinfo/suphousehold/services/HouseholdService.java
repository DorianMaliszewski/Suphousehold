/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.services;

import com.supinfo.suphousehold.entities.Household;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author dorian
 */
public interface HouseholdService {
    List<Household> findAll();

    public Optional<Household> find(Long id);

    public List<Household> search(String s);
}
