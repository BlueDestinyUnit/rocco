package com.jsh.rocco.domains.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jsh.rocco.domains.entities.PropertyAddress;
import com.jsh.rocco.domains.entities.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(of = "id")
public class AvailableProperty {

    private long id;
    private String name;
    private int grade;
    private String intro;

    private PropertyAddress propertyAddress;

    private List<AvailableRoom> rooms;

}
