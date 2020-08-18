package com.lambdaschool.schools.models;

public class ValidationError
{
    private String code; // Field that has the issue
    private String message; // Message related to the validation issue

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
