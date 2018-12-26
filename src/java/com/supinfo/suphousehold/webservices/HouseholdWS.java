/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.suphousehold.webservices;

import com.supinfo.suphousehold.entities.Household;
import com.supinfo.suphousehold.services.HouseholdService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dorian
 */
@Path("/api/household")
public class HouseholdWS {
    @EJB
    private HouseholdService householdService;
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    private List<Household> findAll() {
        return this.householdService.findAll();
    } 
    
    @Path("/search/{s}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    private List<Household> search(@PathParam(value = "s") String s) {
        return this.householdService.search(s);
    }
}
