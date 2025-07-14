/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;

/**
 *
 * @author shake
 */
import exception.OrderNotFoundException;
import exception.mappers.OrderNotFoundExceptionMapper;
import exception.mappers.AuthorNotFoundExceptionMapper;
import exception.mappers.BookNotFoundExceptionMapper;
import exception.mappers.CartNotFoundExceptionMapper;
import exception.mappers.CustomerNotFoundExceptionMapper;
import exception.mappers.InvalidInputExceptionMapper;
import exception.mappers.OutOfStockExceptionMapper;
import resource.BookResource;
import resource.AuthorResource;
import resource.CustomerResource;
import resource.CartResource;
import resource.OrderResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/api") // Base URL http://localhost:8080/BookstoreApi/api/...

public class BookstoreApplication  extends Application{
    
    @Override
 public Set<Class<?>> getClasses() {
  HashSet<Class<?>> classes = new HashSet<>();
  classes.add(BookResource.class);
  classes.add(AuthorResource.class);
  classes.add(CustomerResource.class);
  classes.add(CartResource.class);
  classes.add(OrderResource.class);
  classes.add(OrderNotFoundException.class);
  classes.add(BookNotFoundExceptionMapper.class);
  classes.add(AuthorNotFoundExceptionMapper.class);
  classes.add(CustomerNotFoundExceptionMapper.class);
  classes.add(InvalidInputExceptionMapper.class);
  classes.add(OutOfStockExceptionMapper.class);
  classes.add(CartNotFoundExceptionMapper.class);
  classes.add(OrderNotFoundExceptionMapper.class);
  return classes;
 }
}
    

