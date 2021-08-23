package nl.rabobank.web.error;


import lombok.extern.slf4j.Slf4j;
import nl.rabobank.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handle domain layer exceptions
 */
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler{

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ UnknownPaymentTypeException.class })
    public ErrorMessage handleUnknownPaymentTypeException(final UnknownPaymentTypeException ex) {
        log.debug("handleUnknownPaymentTypeException(), error: {}", ex.getMessage());
        return new ErrorMessage("The request payment type is invalid.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ErrorMessage handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {

        log.debug("handleMethodArgumentNotValidException(), error: {}", ex.getMessage());

        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ErrorMessage(String.join(",", errors));
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ AccountDuplicationException.class })
    public ErrorMessage handleAccountDuplicationException(final AccountDuplicationException ex) {
        log.debug("handleAccountDuplicationException(), error: {}", ex.getMessage());
        return new ErrorMessage(String.format("The account already exist. Account number %s",ex.getAccountNumber()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ AccountNotFoundException.class })
    public ErrorMessage handleAccountNotFoundException(final AccountNotFoundException ex) {
        log.debug("handleAccountNotFoundException(), error: {}", ex.getMessage());
        return new ErrorMessage(String.format("Account not found. Account number %s",ex.getAccountNumber()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ PowerOfAttorneyDuplicationException.class })
    public ErrorMessage handlePowerOfAttorneyDuplicationException(final PowerOfAttorneyDuplicationException ex) {
        log.debug("handlePowerOfAttorneyDuplicationException(), error: {}", ex.getMessage());
        return new ErrorMessage(String.format("Power of Attorney  not found. Account number %s, Grantee Name %s",ex.getAccountNumber(),ex.getGranteeName()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ PowerOfAttorneyNotFoundException.class })
    public ErrorMessage handlePowerOfAttorneyNotFoundException(final PowerOfAttorneyNotFoundException ex) {
        log.debug("handleAccountNotFoundException(), error: {}", ex.getMessage());
        return new ErrorMessage(String.format("Power of Attorney  not found. Account number %s, Grantee Name %s",ex.getAccountNumber(),ex.getGranteeName()));
    }

}