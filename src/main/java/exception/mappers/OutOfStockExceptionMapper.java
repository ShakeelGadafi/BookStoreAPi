/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception.mappers;

/**
 *
 * @author shake
 */
import exception.ErrorMessage;
import exception.OutOfStockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {
    @Override
 public Response toResponse(OutOfStockException exception) {
  ErrorMessage errorMessage = new ErrorMessage("Out of Stock", exception.getMessage());
  return Response.status(Response.Status.CONFLICT)
   .entity(errorMessage)
   .type("application/json")
   .build();
 }
    
}
