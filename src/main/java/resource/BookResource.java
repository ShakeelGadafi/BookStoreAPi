/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resource;

/**
 *
 * @author shake
 */


import model.Book;
import exception.BookNotFoundException;
import exception.InvalidInputException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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


@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

private static Map<Integer, Book> books = new HashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(0);
    
     static {
         
        Book book1 = new Book();
        book1.setId(idCounter.incrementAndGet());
        book1.setTitle("Harry Potter and the Philosopher's Stone");
        book1.setAuthor("J.K. Rowling");
        book1.setIsbn("9780747532699");
        book1.setPublicationYear(1997);
        book1.setPrice(19.99);
        book1.setStock(50);
        books.put(book1.getId(), book1);

        Book book2 = new Book();
        book2.setId(idCounter.incrementAndGet());
        book2.setTitle("The Lord of the Rings");
        book2.setAuthor("J.R.R. Tolkien");
        book2.setIsbn("9780261103573");
        book2.setPublicationYear(1954);
        book2.setPrice(25.50);
        book2.setStock(30);
        books.put(book2.getId(), book2);
    }

    // Static helper method for Java internal use
    public static List<Book> getBooks() {
        return new ArrayList<>(books.values());
    }

    // REST API: Get all books (for Postman / browser)
    @GET
    public Response getAllBooks() {
        List<Book> bookList = new ArrayList<>(books.values());
        return Response.ok(bookList).build();
    }

    @POST
    public Response addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty() ||
            book.getAuthor() == null || book.getAuthor().isEmpty() ||
            book.getIsbn() == null || book.getIsbn().isEmpty() ||
            book.getPublicationYear() <= 0 || book.getPrice() <= 0.0 || book.getStock() < 0) {
            throw new InvalidInputException("All fields are required and must be valid.");
        }

        if (book.getPublicationYear() > 2025) {
            throw new InvalidInputException("Publication year cannot be in the future.");
        }

        book.setId(idCounter.incrementAndGet());
        books.put(book.getId(), book);

        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") int id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException("Book with id " + id + " not found.");
        }
        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        Book existingBook = books.get(id);
        if (existingBook == null) {
            throw new BookNotFoundException("Book with id " + id + " not found.");
        }

        if (updatedBook.getTitle() == null || updatedBook.getTitle().isEmpty() ||
            updatedBook.getAuthor() == null || updatedBook.getAuthor().isEmpty() ||
            updatedBook.getIsbn() == null || updatedBook.getIsbn().isEmpty() ||
            updatedBook.getPublicationYear() <= 0 || updatedBook.getPrice() <= 0.0 || updatedBook.getStock() < 0) {
            throw new InvalidInputException("All fields are required and must be valid.");
        }

        if (updatedBook.getPublicationYear() > 2025) {
            throw new InvalidInputException("Publication year cannot be in the future.");
        }

        updatedBook.setId(id); // Maintain the same ID
        books.put(id, updatedBook);

        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book removedBook = books.remove(id);
        if (removedBook == null) {
            throw new BookNotFoundException("Book with id " + id + " not found.");
        }
        return Response.noContent().build();
    }
}
    

