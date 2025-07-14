/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resource;

/**
 *
 * @author shake
 */

import model.Order;
import model.CartItem;
import model.Book;
import model.Customer;
import exception.CustomerNotFoundException;
import exception.CartNotFoundException;
import exception.BookNotFoundException;
import exception.OrderNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//.
public class OrderResource {
    
 private static Map<Integer, List<Order>> customerOrders = new HashMap<>();
 private static AtomicInteger idCounter = new AtomicInteger(0);


 @POST
 public Response createOrder(@PathParam("customerId") int customerId) {
   Customer customer = CustomerResource.getCustomers().stream().filter(c -> c.getId() == customerId)
   .findFirst()
   .orElse(null);


  if (customer == null) {
  throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
  }


  List<CartItem> cart = CartResource.getCarts().get(customerId);
  if (cart == null || cart.isEmpty()) {
  throw new CartNotFoundException("Cart not found or empty for customer id " + customerId);
  }


  double totalPrice = 0.0;
  for (CartItem item : cart) {
  Book book = BookResource.getBooks().stream()
   .filter(b -> b.getId() == item.getBookId())
   .findFirst()
   .orElse(null);


  if (book == null) {
   throw new BookNotFoundException("Book with id " + item.getBookId() + " not found");
  }


  totalPrice += book.getPrice() * item.getQuantity();
  }


  int orderId = idCounter.incrementAndGet();
  Order order = new Order(orderId, customerId, new ArrayList<>(cart), totalPrice);


  customerOrders.computeIfAbsent(customerId, k -> new ArrayList<>()).add(order);


  CartResource.getCarts().remove(customerId);


  return Response.status(Response.Status.CREATED).entity(order).build();
 }


 @GET
 public List<Order> getOrders(@PathParam("customerId") int customerId) {
  Customer customer = CustomerResource.getCustomers().stream()
   .filter(c -> c.getId() == customerId)
   .findFirst()
   .orElse(null);


  if (customer == null) {
  throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
  }


  List<Order> orders = customerOrders.get(customerId);
  if (orders == null) {
  return new ArrayList<>();
  }


  return orders;
 }


 @GET
 @Path("/{orderId}")
 public Order getOrder(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
  Customer customer = CustomerResource.getCustomers().stream()
   .filter(c -> c.getId() == customerId)
   .findFirst()
   .orElse(null);


  if (customer == null) {
  throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
  }


  List<Order> orders = customerOrders.get(customerId);
  if (orders == null) {
  throw new OrderNotFoundException("Order with id " + orderId + " not found for customer id " + customerId);
  }


  Order order = orders.stream()
   .filter(o -> o.getId() == orderId)
   .findFirst()
   .orElse(null);


  if (order == null) {
  throw new OrderNotFoundException("Order with id " + orderId + " not found for customer id " + customerId);
  }


  return order;
 }
}
    

