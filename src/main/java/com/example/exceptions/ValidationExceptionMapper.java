package com.example.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.validation.ResponseConstraintViolationException;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    private static final Logger LOG = LogUtils.getL7dLogger(ValidationExceptionMapper.class);
    private boolean addMessageToResponse;
    
    @Override
    public Response toResponse(ValidationException exception) {
        Response.Status errorStatus = Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof ConstraintViolationException) { 
            
            StringBuilder responseBody = addMessageToResponse ? new StringBuilder() : null;
            
            final ConstraintViolationException constraint = (ConstraintViolationException) exception;
            
            for (final ConstraintViolation< ? > violation: constraint.getConstraintViolations()) {
                String message = buildErrorMessage(violation);
                if (responseBody != null) {
                    responseBody.append(message).append("\n");
                }
                LOG.log(Level.WARNING, message);
            }
            
            if (!(constraint instanceof ResponseConstraintViolationException)) {
                errorStatus = Response.Status.BAD_REQUEST;
            }
            return buildResponse(errorStatus, responseBody != null ? responseBody.toString() : null);
        } else {
            return buildResponse(errorStatus, addMessageToResponse ? exception.getMessage() : null);
        }
    }

    protected String buildErrorMessage(ConstraintViolation<?> violation) {
        return "Value " 
            + (violation.getInvalidValue() != null ? "'" + violation.getInvalidValue().toString() + "'" : "(null)")
            + " of " + violation.getRootBeanClass().getSimpleName()
            + "." + violation.getPropertyPath()
            + ": " + violation.getMessage();
    }

    protected Response buildResponse(Response.Status errorStatus, String responseText) {
        ResponseBuilder rb = JAXRSUtils.toResponseBuilder(errorStatus);
        rb.type(MediaType.APPLICATION_JSON).entity( new ErrorResponse(490,responseText));
//        if (responseText != null) {
//            rb.type(MediaType.APPLICATION_JSON).entity( new ErrorResponse(490,responseText));
//        }
        return rb.build();
    }

    /**
     * Controls whether to add a constraint validation message to Response or not
     * @param addMessageToResponse add a constraint validation message to Response
     */
    public void setAddMessageToResponse(boolean addMessageToResponse) {
        this.addMessageToResponse = addMessageToResponse;
    }

}
