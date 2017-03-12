package com.example.rs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.example.exceptions.ValidationExceptionMapper;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@ApplicationPath( "api" )
public class JaxRsApiApplication extends Application {
	@Override
	public Set<Object> getSingletons() {
		return new HashSet< Object >(
			Arrays.< Object >asList(
			    new PeopleRestService(),
				new ValidationExceptionMapper(),
				new JacksonJsonProvider()
			)
		);
	}
}
