package com.example.trainticketapp.service;

import com.example.trainticketapp.exceptions.TicketNotFoundException;
import com.example.trainticketapp.pojos.communicators.request.BookTicketRequest;
import com.example.trainticketapp.pojos.communicators.request.UpdateTicketRequest;
import com.example.trainticketapp.pojos.communicators.response.BookTicketResponse;
import com.example.trainticketapp.pojos.communicators.response.FetchAllSectionResponse;
import com.example.trainticketapp.pojos.communicators.response.FetchTicketReceiptResponse;

import java.util.UUID;

/**
 * @author ashutosh
 * date 06/02/25
 */
public interface ITicketService {
    BookTicketResponse bookTicket(BookTicketRequest request) throws TicketNotFoundException;

    Boolean updateTicket(UpdateTicketRequest request) throws TicketNotFoundException;

    FetchTicketReceiptResponse viewUserTicketReceipt(UUID userId) throws TicketNotFoundException;

    Boolean removeUserFromTrain(UUID userId);

    FetchAllSectionResponse viewAllTicketForAllSection(String section);
}
