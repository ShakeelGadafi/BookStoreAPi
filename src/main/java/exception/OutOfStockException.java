/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author shake
 */

public class OutOfStockException extends RuntimeException  {
    public OutOfStockException(String message) {
        super(message);
    }
    
}
