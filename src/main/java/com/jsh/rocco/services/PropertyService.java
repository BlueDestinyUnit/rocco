package com.jsh.rocco.services;

import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {
    @Autowired
    PropertyRepository propertyRepository;

    public void addProperty(Property property){
        propertyRepository.save(property);
    }

    public Property getProperty(long id){
        return this.propertyRepository.findById(id).orElse(null);
    }



}
