package com.example.trainticketapp.dao;

import com.example.trainticketapp.exceptions.TicketNotFoundException;
import com.example.trainticketapp.pojos.entities.TicketEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author ashutosh
 * date 06/02/25
 */
public interface ITicketDao {
    TicketEntity save(TicketEntity ticketEntity);

    List<TicketEntity> saveAll(List<TicketEntity> ticketEntities);

    void deleteAll(List<TicketEntity> ticketEntities);

    List<TicketEntity> findAllTicketsForUser(UUID userId);

    TicketEntity findById(UUID ticketId) throws TicketNotFoundException;
}
