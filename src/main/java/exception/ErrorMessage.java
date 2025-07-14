/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author shake
 */
public class ErrorMessage {
 private String error;
 private String message;


 public ErrorMessage() {
 }


 public ErrorMessage(String error, String message) {
  this.error = error;
  this.message = message;
 }


 public String getError() {
  return error;
 }


 public void setError(String error) {
  this.error = error;
 }


 public String getMessage() {
  return message;
 }


 public void setMessage(String message) {
  this.message = message;
 }
}

