package com.example.trainticketapp.exceptions;

/**
 * @author ashutosh
 * date 06/02/25
 */
public class TicketNotFoundException extends Exception {
    public TicketNotFoundException(String message) {
        super(message);
    }

    public TicketNotFoundException() {
        super("Ticket Not Found Exception");
    }
}
