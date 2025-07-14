/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resource;

/**
 *
 * @author shake
 */

import model.CartItem;
 import model.Book;
 import model.Customer;
 import exception.BookNotFoundException;
 import exception.CustomerNotFoundException;
 import exception.OutOfStockException;
 import exception.CartNotFoundException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.DELETE;
 import javax.ws.rs.GET;
 import javax.ws.rs.POST;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.PathParam;
 import javax.ws.rs.Produces;
 import javax.ws.rs.core.MediaType;
 import javax.ws.rs.core.Response;
import org.json.JSONException;
 import org.json.JSONObject;


@Path("/customers/{customerId}/cart")
 @Produces(MediaType.APPLICATION_JSON)
 @Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    private static Map<Integer, List<CartItem>> carts = new HashMap<>();

    @POST
    @Path("/items")
    public Response addItemToCart(@PathParam("customerId") int customerId, String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        int bookId = json.getInt("bookId");
        int quantity = json.getInt("quantity");

        Customer customer = CustomerResource.getCustomers().stream()
                .filter(c -> c.getId() == customerId)
                .findFirst()
                .orElse(null);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
        }

        Book book = BookResource.getBooks().stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .orElse(null);

        if (book == null) {
            throw new BookNotFoundException("Book with id " + bookId + " not found");
        }

        if (book.getStock() < quantity) {
            throw new OutOfStockException("Not enough stock for book with id " + bookId);
        }

        List<CartItem> cart = carts.computeIfAbsent(customerId, k -> new ArrayList<>());
        CartItem item = new CartItem(bookId, quantity);
        cart.add(item);

        // Decrease stock
        book.setStock(book.getStock() - quantity);

        return Response.status(Response.Status.CREATED).entity(cart).build();
    }

    @GET
    public Response getCart(@PathParam("customerId") int customerId) {
        Customer customer = CustomerResource.getCustomers().stream()
                .filter(c -> c.getId() == customerId)
                .findFirst()
                .orElse(null);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
        }

        List<CartItem> cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer id " + customerId);
        }

        return Response.ok(cart).build();
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(@PathParam("customerId") int customerId,
                                   @PathParam("bookId") int bookId,
                                   String jsonString) throws JSONException {

        JSONObject json = new JSONObject(jsonString);
        int quantity = json.getInt("quantity");

        Customer customer = CustomerResource.getCustomers().stream()
                .filter(c -> c.getId() == customerId)
                .findFirst()
                .orElse(null);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
        }

        Book book = BookResource.getBooks().stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .orElse(null);

        if (book == null) {
            throw new BookNotFoundException("Book with id " + bookId + " not found");
        }

        List<CartItem> cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer id " + customerId);
        }

        for (CartItem item : cart) {
            if (item.getBookId() == bookId) {
                // Restore previous stock
                book.setStock(book.getStock() + item.getQuantity());

                if (book.getStock() < quantity) {
                    throw new OutOfStockException("Not enough stock for book with id " + bookId);
                }

                // Update quantity
                item.setQuantity(quantity);

                // Deduct new stock
                book.setStock(book.getStock() - quantity);

                return Response.ok(cart).build();
            }
        }

        throw new BookNotFoundException("Book with id " + bookId + " not found in cart");
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response deleteCartItem(@PathParam("customerId") int customerId,
                                   @PathParam("bookId") int bookId) {

        Customer customer = CustomerResource.getCustomers().stream()
                .filter(c -> c.getId() == customerId)
                .findFirst()
                .orElse(null);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
        }

        Book book = BookResource.getBooks().stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .orElse(null);

        if (book == null) {
            throw new BookNotFoundException("Book with id " + bookId + " not found");
        }

        List<CartItem> cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer id " + customerId);
        }

        boolean removed = cart.removeIf(item -> item.getBookId() == bookId);

        if (removed) {
            // (Optional) If you want to restore stock, you could update stock here.
            return Response.noContent().build();
        } else {
            throw new BookNotFoundException("Book with id " + bookId + " not found in cart");
        }
    }

    public static Map<Integer, List<CartItem>> getCarts() {
        return carts;
    }
}
