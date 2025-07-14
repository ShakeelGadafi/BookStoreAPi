/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author shake
 */

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id ;
    private String customerEmail;
    private List<CartItem> items;
    
    public Cart() {
        this.items = new ArrayList<>();
    }

    public Cart(String customerEmail, List<CartItem> items) {
    this.customerEmail = customerEmail;
    this.items = items;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

   


    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void addItem(CartItem item) {
        this.items.add(item);
    }

    
    public void removeItem(int bookId) {
        this.items.removeIf(item -> item.getBookId() == bookId);
    }
}
    
    
