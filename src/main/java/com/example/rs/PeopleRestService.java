package com.example.rs;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.hibernate.validator.constraints.Length;

import com.example.exceptions.PersonNotFoundException;
import com.example.model.Person;


@Path( "/people" ) 
public class PeopleRestService {
    
    public PeopleRestService() {
		
	}
    
    @Produces( { MediaType.APPLICATION_JSON } )
    @GET
    public @Valid Response getPeople( 
    		@Min( 1 ) @QueryParam( "count" ) @DefaultValue( "1" ) final int count ) {
    	
        final Person person = new Person("test@me.com");
        person.setFirstName("yuya");
        person.setLastName("Nakahira");
    	
        //throw new PersonNotFoundException(person);
        
        return Response.ok(person).build();
        
    }
    
    @Valid
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response addPerson2(Person person){
    	
    	return Response.ok(person).build();
    	
    }
    
    
    @Valid
    @Produces( { MediaType.APPLICATION_JSON  } )
    @POST
    @Path("/sss")
    public Response addPerson( @Context final UriInfo uriInfo,
            @NotNull @Length( min = 5, max = 255 ) @FormParam( "email" ) final String email, 
            @FormParam( "firstName" ) final String firstName, 
            @FormParam( "lastName" ) final String lastName ) {        
        final Person person = addPerson( email, firstName, lastName );
        return Response.created( uriInfo.getRequestUriBuilder().path( email ).build() ).entity( person ).build();
    }
    
    private Person addPerson( final String email, final String firstName, final String lastName ) {
        final Person person = new Person( email );
        person.setFirstName( firstName );
        person.setLastName( lastName );
                
        
        return  person;
    }

}
