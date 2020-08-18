package com.lambdaschool.schools.exceptions;

import com.lambdaschool.schools.services.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomErrorDetails extends DefaultErrorAttributes
{
    @Autowired
    HelperFunctions helperFunctions;

    @Override
    public Map<String, Object> getErrorAttributes(
        WebRequest webRequest, // contains the actual error message that was generated
        boolean includeStackTrace)
    {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace); // grab existing attributes

        Map<String, Object> errorDetails = new LinkedHashMap<>(); // make our own collection of errors in an ordered hash map
        errorDetails.put("title", errorAttributes.get("error")); // proceed through the known attributes to construct our details
        errorDetails.put("status", errorAttributes.get("status"));
        errorDetails.put("detail", errorAttributes.get("message"));
        errorDetails.put("timestamp", errorAttributes.get("timestamp"));
        errorDetails.put("developerMessage", "path = " + errorAttributes.get("path"));

        errorDetails.put("errors", helperFunctions.getConstraintViolation(this.getError(webRequest)));

        return errorDetails;
    }
}
