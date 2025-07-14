/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resource;

/**
 *
 * @author shake
 */
import model.Customer;
import exception.CustomerNotFoundException;
import exception.InvalidInputException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;

import org.json.JSONObject;


@Path("/customers")
 @Produces(MediaType.APPLICATION_JSON)
 @Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
  private static Map<Integer, Customer> customers = new HashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(0);
    
    
    static {
    Customer customer1 = new Customer();
    customer1.setId(idCounter.incrementAndGet());
    customer1.setName("John Doe");
    customer1.setEmail("john@example.com");
    customer1.setPassword("password123");
    customers.put(customer1.getId(), customer1);

    Customer customer2 = new Customer();
    customer2.setId(idCounter.incrementAndGet());
    customer2.setName("Jane Smith");
    customer2.setEmail("jane@example.com");
    customer2.setPassword("password456");
    customers.put(customer2.getId(), customer2);
}

    // Static helper method to get customers (for Java code use)
    public static List<Customer> getCustomers() {
        return new ArrayList<>(customers.values());
    }

    // REST endpoint to get customers (for Postman/API use)
    @GET
    public Response getAllCustomers() {
        List<Customer> customerList = new ArrayList<>(customers.values());
        return Response.ok(customerList).build();
    }

    @POST
    public Response addCustomer(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        String name = json.optString("name");
        String email = json.optString("email");
        String password = json.optString("password");

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new InvalidInputException("Name, email, and password are required.");
        }

        Customer customer = new Customer();
        customer.setId(idCounter.incrementAndGet());
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customers.put(customer.getId(), customer);

        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") int id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, String jsonString) throws JSONException {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }

        JSONObject json = new JSONObject(jsonString);
        String name = json.optString("name");
        String email = json.optString("email");
        String password = json.optString("password");

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new InvalidInputException("Name, email, and password are required.");
        }

        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customers.put(id, customer);

        return Response.ok(customer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer removedCustomer = customers.remove(id);
        if (removedCustomer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
        return Response.noContent().build();
    }
}

