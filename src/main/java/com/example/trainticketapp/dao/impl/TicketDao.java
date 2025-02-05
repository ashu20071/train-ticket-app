package com.example.trainticketapp.dao.impl;

import com.example.trainticketapp.dao.ITicketDao;
import com.example.trainticketapp.exceptions.TicketNotFoundException;
import com.example.trainticketapp.pojos.entities.TicketEntity;
import com.example.trainticketapp.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author ashutosh
 * date 06/02/25
 */

@Service
public class TicketDao implements ITicketDao {
    private final TicketRepository ticketRepository;

    public TicketDao(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketEntity save(TicketEntity ticketEntity) {
        return ticketRepository.save(ticketEntity);
    }

    @Override
    public List<TicketEntity> saveAll(List<TicketEntity> ticketEntities) {
        return ticketRepository.saveAll(ticketEntities);
    }

    @Override
    public void deleteAll(List<TicketEntity> ticketEntities) {
        ticketRepository.deleteAll(ticketEntities);
    }

    @Override
    public List<TicketEntity> findAllTicketsForUser(UUID userId) {
        return ticketRepository.findAllByUserIdAndIsEnabledTrue(userId);
    }

    @Override
    public TicketEntity findById(UUID ticketId) throws TicketNotFoundException {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(
                String.format("Ticket with id : %s", ticketId)
        ));
    }
}
