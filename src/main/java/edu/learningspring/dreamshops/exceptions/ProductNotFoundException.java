package edu.learningspring.dreamshops.exceptions;

public class ProductNotFoundException extends RuntimeException
{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
