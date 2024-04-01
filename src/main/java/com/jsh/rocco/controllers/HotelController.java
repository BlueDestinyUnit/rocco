package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    private final PropertyService propertyService;

    @Autowired
    HotelController(PropertyService propertyService){
        this.propertyService = propertyService;
    }

    @GetMapping("/detail")
    public void getHotels(Property property, Model model) {
        property = propertyService.getProperty(property.getId());
        model.addAttribute("property",property);
    }

}
