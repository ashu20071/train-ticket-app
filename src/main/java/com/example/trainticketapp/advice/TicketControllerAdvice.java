package com.example.trainticketapp.advice;

import com.example.trainticketapp.exceptions.TicketNotFoundException;
import com.example.trainticketapp.pojos.communicators.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ashutosh
 * date 06/02/25
 */

@Slf4j
@ControllerAdvice
@RestController
@Order(0)
public class TicketControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TicketNotFoundException.class)
    public BaseResponse ticketNotFoundException(TicketNotFoundException exception) {
        log.error("TicketNotFoundException thrown", exception);
        return BaseResponse.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();
    }
}
