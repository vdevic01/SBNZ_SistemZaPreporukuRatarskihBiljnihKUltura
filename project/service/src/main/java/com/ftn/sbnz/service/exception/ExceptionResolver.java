package com.ftn.sbnz.service.exception;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@ControllerAdvice
public class ExceptionResolver {


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(BadRequestException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> forbiddenException(ForbiddenException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, headers, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> processConversionException(HttpMessageNotReadableException e) {

        String msg = null;
        Throwable cause = e.getCause();

        if (cause instanceof JsonParseException) {
            JsonParseException jpe = (JsonParseException) cause;
            msg = jpe.getMessage();
        } else if (cause instanceof MismatchedInputException) {
            MismatchedInputException mie = (MismatchedInputException) cause;
            if (mie.getPath() != null && !mie.getPath().isEmpty()) {
                msg = "Field " + mie.getPath().get(0).getFieldName() + " format is not valid";
            } else {
                msg = "Invalid request message";
            }
        } else if (cause instanceof JsonMappingException) {
            JsonMappingException jme = (JsonMappingException) cause;
            msg = jme.getOriginalMessage();
            if (jme.getPath() != null && !jme.getPath().isEmpty()) {
                msg = "Field " + jme.getPath().get(0).getFieldName() + " format is not valid";
            }
        }

        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> argumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>("Field "+ exception.getName()+" is not valid!", headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<String> handleConstraintViolationException(MethodArgumentNotValidException exception) {
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        return new ResponseEntity<>(errorList.get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<String> illegalArgument(IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<String> dateTimeParse(DateTimeParseException exception) {
        return new ResponseEntity<>("Value " + exception.getParsedString() + " is not valid DateTime format", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
