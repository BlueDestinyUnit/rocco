package com.jsh.rocco.sample;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
	private int roomNum;		//객실번호
	private String name;		//객실명
	private int capacity;		//최대 투숙갯수
	private double price;		//가격
	private String intro;		//객실에 대한 소개
}
