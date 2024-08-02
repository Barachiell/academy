package com.ctw.workstation.infrastructure.exceptionMappers;

import com.ctw.workstation.infrastructure.error.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Internal server error", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()))
                .build();
    }
}
