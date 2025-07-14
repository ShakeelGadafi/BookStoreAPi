/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author shake
 */

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BookNotFoundException extends WebApplicationException {
    public BookNotFoundException(String message) {
  super(Response.status(Response.Status.NOT_FOUND)
   .entity(new ErrorMessage("Book Not Found", message))
   .type(MediaType.APPLICATION_JSON).build());
 }
    
}
