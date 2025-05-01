package com.codingplatform.coding_platform_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFoundException(
            UserNotFoundException userNotFoundException,
            WebRequest webRequest)
    {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(userNotFoundException.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorObject> handleRoleNotFoundException(
            RoleNotFoundException roleNotFoundException,
            WebRequest webRequest)
    {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(roleNotFoundException.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProblemAlreadyExistException.class)
    public ResponseEntity<ErrorObject> handleProblemAlreadyExistException(
            ProblemAlreadyExistException problemAlreadyExistException,
            WebRequest webRequest)
    {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage(problemAlreadyExistException.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProblemNotFoundException.class)
    public ResponseEntity<ErrorObject> handleProblemNotFoundException(
            ProblemNotFoundException problemNotFoundException,
            WebRequest webRequest)
    {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(problemNotFoundException.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ErrorObject> handleTagNotFoundException(
            TagNotFoundException tagNotFoundException,
            WebRequest webRequest)
    {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(tagNotFoundException.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorObject> handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException,
            WebRequest webRequest)
    {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(illegalArgumentException.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubmissionNotFoundException.class)
    public ResponseEntity<ErrorObject> handleSubmissionNotFoundException(
            SubmissionNotFoundException submissionNotFoundException,
            WebRequest webRequest)
    {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(submissionNotFoundException.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagAlreadyExistException.class)
    public ResponseEntity<ErrorObject> handleTagAlreadyExistException(
            TagAlreadyExistException tagAlreadyExistException,
            WebRequest webRequest)
    {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage(tagAlreadyExistException.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleValidateException(
            MethodArgumentNotValidException methodArgumentNotValidException,
            WebRequest webRequest)
    {
        List<String> errors = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(String.join("; ", errors));
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorObject> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException httpMessageNotReadableException,
            WebRequest webRequest)
    {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(httpMessageNotReadableException.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }
}
