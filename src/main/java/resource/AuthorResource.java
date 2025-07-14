/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resource;

/**
 *
 * @author shake
 */

import model.Author;
import exception.AuthorNotFoundException;
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


@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    private static Map<Integer, Author> authors = new HashMap<>();
 private static AtomicInteger idCounter = new AtomicInteger(0);
 
 static {
        Author author1 = new Author(idCounter.incrementAndGet(), "Princess Sandy the Great", "Saving aiya.");
        Author author2 = new Author(idCounter.incrementAndGet(), "George Orwell", "Author of 1984 and Animal Farm.");
        Author author3 = new Author(idCounter.incrementAndGet(), "J.R.R. Tolkien", "Author of The Lord of the Rings.");
        
        authors.put(author1.getId(), author1);
        authors.put(author2.getId(), author2);
        authors.put(author3.getId(), author3);
    }


 @POST
 public Response addAuthor(Author author) {
  if (author.getName() == null || author.getName().isEmpty() || author.getBiography() == null
   || author.getBiography().isEmpty()) {
  throw new InvalidInputException("Name and biography are required");
  }


  author.setId(idCounter.incrementAndGet());
  authors.put(author.getId(), author);
  return Response.status(Response.Status.CREATED).entity(author).build();
 }


 @GET
 public List<Author> getAuthors() {
  return new ArrayList<>(authors.values());
 }


 @GET
 @Path("/{id}")
 public Author getAuthor(@PathParam("id") int id) {
  Author author = authors.get(id);
  if (author == null) {
  throw new AuthorNotFoundException("Author with id " + id + " not found");
  }
  return author;
 }


 @PUT
 @Path("/{id}")
 public Author updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
  Author author = authors.get(id);
  if (author == null) {
  throw new AuthorNotFoundException("Author with id " + id + " not found");
  }


  if (updatedAuthor.getName() == null || updatedAuthor.getName().isEmpty() || updatedAuthor.getBiography() == null
   || updatedAuthor.getBiography().isEmpty()) {
  throw new InvalidInputException("Name and biography are required");
  }


  updatedAuthor.setId(id);
  authors.put(id, updatedAuthor);
  return updatedAuthor;
 }


 @DELETE
 @Path("/{id}")
 public Response deleteAuthor(@PathParam("id") int id) {
  Author author = authors.remove(id);
  if (author == null) {
  throw new AuthorNotFoundException("Author with id " + id + " not found");
  }
  return Response.noContent().build();
 }
}
    

