package com.example.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.model.Person;

public class PersonNotFoundException extends WebApplicationException {
	private static final long serialVersionUID = -2894269137259898072L;
	
	public PersonNotFoundException(Person person) {
        		
		super(
			Response
				.status( 202 )
				.entity(person)
				.type(MediaType.APPLICATION_JSON)
				.build()
		);
	}
}
