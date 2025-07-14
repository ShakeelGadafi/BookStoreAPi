/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception.mappers;

/**
 *
 * @author shake
 */
import exception.OrderNotFoundException;
import exception.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OrderNotFoundExceptionMapper implements ExceptionMapper<OrderNotFoundException> {
    @Override
    public Response toResponse(OrderNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage("Order Not Found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage)
                .type("application/json")
                .build();
    }
    
}
