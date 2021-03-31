package com.practice.cooking.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Validated
public class ErrorController extends AbstractErrorController {

    private final Logger logger = getLogger(ErrorController.class);

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        HttpStatus httpStatus = getStatus(request);
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, true);

        if (httpStatus == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(httpStatus);
        }

        Object path = errorAttributes.get("path");
        Object status = errorAttributes.get("status");
        Object error = errorAttributes.get("error");
        Object message = errorAttributes.get("message");
        
        errorAttributes.replace("message", "Could not process request due to " + httpStatus.toString());

        logger.info("An error occurred while accessing {}: {} {}, {}", path, status, error, message);

        return new ResponseEntity<>(errorAttributes, httpStatus);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
