package ua.mai.servs.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//import ua.telesens.o320.tif.common.ServiceFault;
import ua.mai.servs.exceptions.CustomException;
import ua.mai.servs.exceptions.ResourceAlreadyExists;
import ua.mai.servs.exceptions.ResourceNotFoundException;
import ua.mai.servs.exceptions.UnauthorizedException;
import ua.mai.servs.exceptions.ResourceException;

import javax.validation.ConstraintViolationException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{}", ex.getMessage());
        return super.handleHttpMediaTypeNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{}", ex.getMessage());
        return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getBindingResult() != null && ex.getBindingResult().getErrorCount() > 0) {
            String defaultMessageKey = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(customResponseBuild(defaultMessageKey), HttpStatus.BAD_REQUEST);
        } else {
            log.error("{}", ex.getMessage());
            return super.handleMethodArgumentNotValid(ex, headers, status, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{}", ex.getMessage());
        return super.handleMissingPathVariable(ex, headers, status, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceException> resourceNotFound(ResourceNotFoundException ex) {
        String message = ex.getCause().getMessage();
        log.error("{}", message);
        ResourceException response = ResourceException.builder()
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> missingRequestHeader(MissingRequestHeaderException ex) {
        // FIXME: 14-Jan-21 bolt on impl
        return new ResponseEntity<>(customResponseBuild("Missing Request Header - ".concat(ex.getHeaderName())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolation(ConstraintViolationException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        if (!ex.getConstraintViolations().isEmpty()) {
            String errorKey = ex.getConstraintViolations().stream().findFirst().get().getMessage();
            return new ResponseEntity<>(customResponseBuild(errorKey), httpStatus);
        }
        log.error("{}", ex.getCause().getMessage());
        return new ResponseEntity<>(ex.getMessage(), httpStatus);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ResourceException> resourceAlreadyExists(ResourceAlreadyExists ex) {
        return new ResponseEntity<>(customResponseBuild(ex), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ServiceFault.class)
//    public ResponseEntity<ResourceException> serviceFaultException(ServiceFault ex) {
//        return new ResponseEntity<>(customResponseBuild(ex), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResourceException> customException(CustomException ex) {
        return new ResponseEntity<>(customResponseBuild(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResourceException> unauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(customResponseBuild(ex), HttpStatus.UNAUTHORIZED);
    }

    private <T extends Exception> ResourceException customResponseBuild(T ex) {
        String message = ex.getMessage();
        return customResponseBuild(message);
    }

    private ResourceException customResponseBuild(String message) {
        log.error("{}", message);
        return ResourceException.builder()
                .errorDescription(message)
                .build();
    }

}
