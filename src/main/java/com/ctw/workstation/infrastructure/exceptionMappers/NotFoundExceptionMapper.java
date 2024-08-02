package com.ctw.workstation.infrastructure.exceptionMappers;

import com.ctw.workstation.infrastructure.error.ErrorResponse;
import com.ctw.workstation.infrastructure.exceptions.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(e.getMessage(), Response.Status.NOT_FOUND.getStatusCode()))
                .build();
    }
}
