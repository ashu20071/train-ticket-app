package com.example.trainticketapp.repositories;

import com.example.trainticketapp.pojos.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author ashutosh
 * date 06/02/25
 */
public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
    List<TicketEntity> findAllByUserIdAndIsEnabledTrue(UUID userId);
}
