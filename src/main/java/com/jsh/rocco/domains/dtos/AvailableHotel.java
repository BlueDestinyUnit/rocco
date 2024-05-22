package com.jsh.rocco.domains.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(of = "id")
public class AvailableHotel {

    private long id;
    private String name;
    private int grade;
    private String intro;
    private String region;
    private String street1;
    private String street2;
    private String zipCode;
    private List<AvailableRoom> rooms;

}
