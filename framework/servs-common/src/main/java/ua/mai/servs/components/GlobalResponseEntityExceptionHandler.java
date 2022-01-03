package ua.mai.servs.components;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//import ua.telesens.o320.tif.common.ServiceFault;
import ua.mai.servs.exceptions.CustomException;
import ua.mai.servs.exceptions.ResourceAlreadyExists;
import ua.mai.servs.exceptions.ResourceNotFoundException;
import ua.mai.servs.exceptions.UnauthorizedException;
import ua.mai.servs.exceptions.ResourceException;
import org.springframework.security.core.AuthenticationException;

import javax.validation.ConstraintViolationException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleHttpMediaTypeNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getBindingResult() != null && ex.getBindingResult().getErrorCount() > 0) {
            logError(ex, headers, status, request);
            String defaultMessageKey = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(customResponseBuild(defaultMessageKey), HttpStatus.BAD_REQUEST);
        } else {
            logError(ex, headers, status, request);
            return super.handleMethodArgumentNotValid(ex, headers, status, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleMissingPathVariable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleServletRequestBindingException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleConversionNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleHttpMessageNotWritable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleMissingServletRequestPart(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleBindException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        logError(ex, headers, status, request);
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
                                                                        HttpHeaders headers, HttpStatus status,
                                                                        WebRequest webRequest) {
        logError(ex, headers, status, webRequest);
        return super.handleAsyncRequestTimeoutException(ex, headers, status, webRequest);
    }

//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
//                                                             HttpStatus status, WebRequest request) {
//        logError(ex, headers, status, request);
//        return super.handleExceptionInternal(ex, body, headers, status, request);
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceException> resourceNotFound(ResourceNotFoundException ex) {
        String message = ex.getCause().getMessage();
        logError(ex, null, null, null);
        ResourceException response = ResourceException.builder()
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> missingRequestHeader(MissingRequestHeaderException ex) {
        // FIXME: 14-Jan-21 bolt on impl
        logError(ex, null, null, null);
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
        logError(ex, null, null, null);
        return new ResponseEntity<>(customResponseBuild(ex), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ServiceFault.class)
//    public ResponseEntity<ResourceException> serviceFaultException(ServiceFault ex) {
//        return new ResponseEntity<>(customResponseBuild(ex), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResourceException> customException(CustomException ex) {
        logError(ex, null, null, null);
        return new ResponseEntity<>(customResponseBuild(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResourceException> unauthorizedException(UnauthorizedException ex) {
        logError(ex, null, null, null);
        return new ResponseEntity<>(customResponseBuild(ex), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResourceException> authenticationException(UnauthorizedException ex) {
        logError(ex, null, null, null);
        return new ResponseEntity<>(customResponseBuild(ex), HttpStatus.UNAUTHORIZED);
    }

    private <T extends Exception> ResourceException customResponseBuild(T ex) {
        logError(ex, null, null, null);
        String message = ex.getMessage();
        return customResponseBuild(message);
    }

    private ResourceException customResponseBuild(String message) {
        return ResourceException.builder()
                .errorDescription(message)
                .build();
    }

    protected void logError(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("ERROR : {}{}: {}", (status != null ? status + ": " : ""), ex.getClass().getSimpleName(), ex.getMessage());
        log.debug("{}", ExceptionUtils.getStackTrace(ex));
    }

}
