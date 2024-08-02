package com.ctw.workstation.infrastructure.exceptionMappers;

import com.ctw.workstation.infrastructure.error.ErrorResponse;
import com.ctw.workstation.infrastructure.exceptions.ConflictException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {
    @Override
    public Response toResponse(ConflictException e) {
        return Response.status(Response.Status.CONFLICT)
                .entity(new ErrorResponse(e.getMessage(), Response.Status.CONFLICT.getStatusCode()))
                .build();
    }
}
