package com.jsh.rocco.domains.dtos;


import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;





@Data
@ToString
public class FindHotel {
    private String propertyRegion;
    private int capacity;
    private int customers;
    private String arrivalDate;
    private String departureDate;

}
