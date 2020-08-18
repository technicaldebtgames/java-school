package com.lambdaschool.schools.handlers;

import com.lambdaschool.schools.exceptions.StudentNotFoundException;
import com.lambdaschool.schools.models.ErrorDetail;
import com.lambdaschool.schools.services.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{

    @Autowired
    HelperFunctions helperFunctions;

    public RestExceptionHandler()
    {
        super(); // not sure if including this constructor is necessary, but it might be..?
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<?> handleStudentNotFoundException(StudentNotFoundException studentNotFoundException)
    {

        ErrorDetail errorDetail = new ErrorDetail();

        errorDetail.setTimestamp((new Date())); // might be off by a few ms
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Student Not Found Exception");
        errorDetail.setDetail("Found an error with School: " + studentNotFoundException.getMessage());
        errorDetail.setDeveloperMessage(studentNotFoundException.getClass().getName());

        errorDetail.setErrors(helperFunctions.getConstraintViolation(studentNotFoundException));

        // custom error response created and returned as ResponseEntity
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal( // a ? can be used in the type, but Object is more backwards-compatible
        Exception ex,
        Object body,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();

        errorDetail.setTimestamp((new Date())); // might be off by a few ms
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Rest Server Generic Exception");
        errorDetail.setDetail("Found an issue with School: " + ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex)); // can also be derived from request, but spring has this info in ex

        return new ResponseEntity<>(errorDetail, null, status);
    }
}
