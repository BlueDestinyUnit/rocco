package com.jsh.rocco.sample;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class Customer {
	private String firstName;
	private String lastName;
	private Date birtDate;
	private String phone;
	private Address address;
}
