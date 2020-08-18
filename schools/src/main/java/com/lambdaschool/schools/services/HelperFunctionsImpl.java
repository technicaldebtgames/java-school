package com.lambdaschool.schools.services;

import com.lambdaschool.schools.models.ValidationError;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "helperFunctions")
public class HelperFunctionsImpl implements HelperFunctions
{
    @Override
    public List<ValidationError> getConstraintViolation(Throwable cause)
    {
        while ((cause != null) && !(cause instanceof ConstraintViolationException)) // we want to find a specific error type, a constraint violation
        {
            cause = cause.getCause();
        };

        List<ValidationError> listVE = new ArrayList<>();

        if (cause != null) // constraint violation found
        {
            ConstraintViolationException ex = (ConstraintViolationException) cause;
            for (ConstraintViolation cv : ex.getConstraintViolations())
            {
                ValidationError newVE = new ValidationError();
                newVE.setCode(cv.getInvalidValue().toString());
                newVE.setMessage(cv.getMessage()); // go through each conVio and create a valErr
                listVE.add(newVE);
            }
        }

        return listVE;

    }
}
