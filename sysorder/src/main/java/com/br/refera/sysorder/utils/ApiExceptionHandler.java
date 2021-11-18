package com.br.refera.sysorder.utils;


import com.br.refera.sysorder.model.ErrorResponse;
import com.br.refera.sysorder.service.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private final String DEFAULT_ERROR_MESSAGE = "Contacte o administrador com o código %s";

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<Object> handleApiException(
            ApiException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.valueOf(ex.getStatusCode()), request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request){
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String code = status.getReasonPhrase();
        String description = ex.getClass().getSimpleName();
        String message = String.format(DEFAULT_ERROR_MESSAGE, UUID.randomUUID().toString());
        if (ex instanceof ApiException) {
            description = ((ApiException) ex).getReason();
            message = ex.getMessage();
        } else if(ex instanceof MethodArgumentNotValidException){
            StringBuilder messageBuilder = new StringBuilder();
            BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                messageBuilder.append("Erro no campo: ").append(error.getField())
                        .append(" - mensagem de erro: ").append(error.getDefaultMessage());
                if(fieldErrors.indexOf(error) < (fieldErrors.size() -1)){
                    messageBuilder.append(" | ");
                }
            });
            description = messageBuilder.toString();
        } else if(ex instanceof ConstraintViolationException) {
            StringBuilder messageBuilder = new StringBuilder();
            AtomicInteger index = new AtomicInteger(0);
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex).getConstraintViolations();
            constraintViolations.forEach(violation -> {
                String field = substringAfter(violation.getPropertyPath().toString(), ".");
                messageBuilder.append("Erro no campo: ").append(field).append(" - mensagem de erro: ").append(violation.getMessage());
                if (index.getAndIncrement() < constraintViolations.size() - 1) {
                    messageBuilder.append(" | ");
                }
            });
            description = messageBuilder.toString();
        }
        return super.handleExceptionInternal(ex, ErrorResponse
                .builder()
                .code(code)
                .description(description)
                .message(message)
                .build(), headers, status, request);
    }

    private String substringAfter(String str, String separator){
        if (StringUtils.isEmpty(str)) {
            return str;
        } else if (separator == null) {
            return "";
        } else {
            int pos = str.indexOf(separator);
            return pos == -1 ? "" : str.substring(pos + separator.length());
        }
    }
}