/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception.mappers;

/**
 *
 * @author shake
 */

import exception.InvalidInputException;
import exception.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {
    @Override
    public Response toResponse(InvalidInputException exception) {
        ErrorMessage errorMessage = new ErrorMessage("Invalid Input", exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .type("application/json")
                .build();
    }
    
}
