/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author shake
 */

public class InvalidInputException extends RuntimeException {
   public InvalidInputException(String message) {
        super(message);
    }
}
