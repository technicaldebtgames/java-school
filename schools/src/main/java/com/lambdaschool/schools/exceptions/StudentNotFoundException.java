package com.lambdaschool.schools.exceptions;

public class StudentNotFoundException extends RuntimeException
{
    public StudentNotFoundException(String message)
    {
        super(message); // all we ended up doing here was adding a custom message
    }
}
