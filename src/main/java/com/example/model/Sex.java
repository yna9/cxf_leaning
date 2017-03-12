package com.example.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Sex {
	MALE("male"),
	FEMALE("female");
	
	private final String value;
	
	Sex(String sex){
		this.value = sex;
	}
	
	@JsonValue
	public String value(){
		return value;
	}
	
	public static Sex fromValue(String typeCode){
		for (Sex s : Sex.values() ){
			if(s.value == typeCode ){
				return s;
			}
		}
	    throw new IllegalArgumentException("Invalid Status type code: " + typeCode);        

	}
	
	@Override
	public String toString(){
		return value;
	}
}
