package com.example.trainticketapp.controller;

import com.example.trainticketapp.exceptions.TicketNotFoundException;
import com.example.trainticketapp.pojos.communicators.request.BookTicketRequest;
import com.example.trainticketapp.pojos.communicators.request.UpdateTicketRequest;
import com.example.trainticketapp.pojos.communicators.response.BaseResponse;
import com.example.trainticketapp.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author ashutosh
 * date 06/02/25
 */

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class TrainTicketController {
    private final ITicketService ticketService;

    public TrainTicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/ticket/{userId}")
    public ResponseEntity<BaseResponse> getAllUserTickets(@PathVariable(value = "userId") UUID userId)
            throws TicketNotFoundException {
        return ResponseEntity.ok(BaseResponse.builder()
                .data(ticketService.viewUserTicketReceipt(userId))
                .code(HttpStatus.OK)
                .build());
    }

    @GetMapping("/fetch/{section}")
    public ResponseEntity<BaseResponse> getAllUserTickets(@PathVariable(value = "section") String section) {
        return ResponseEntity.ok(BaseResponse.builder()
                .data(ticketService.viewAllTicketForAllSection(section))
                .code(HttpStatus.OK)
                .build());
    }

    @PostMapping("/book-ticket")
    public ResponseEntity<BaseResponse> createTicket(@RequestBody BookTicketRequest request)
            throws TicketNotFoundException {
        return ResponseEntity.ok(BaseResponse.builder()
                .data(ticketService.bookTicket(request))
                .code(HttpStatus.OK)
                .build());
    }

    @PutMapping("/ticket/{ticketId}")
    public ResponseEntity<BaseResponse> updateTicket(@RequestBody UpdateTicketRequest request)
            throws TicketNotFoundException {
        return ResponseEntity.ok(BaseResponse.builder()
                .data(ticketService.updateTicket(request))
                .code(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/ticket/{userId}")
    public ResponseEntity<BaseResponse> deleteAllUserTicket(@PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.ok(BaseResponse.builder()
                .data(ticketService.removeUserFromTrain(userId))
                .code(HttpStatus.OK)
                .build());
    }
}
