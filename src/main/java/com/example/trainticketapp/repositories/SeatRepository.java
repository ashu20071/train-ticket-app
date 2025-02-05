package com.example.trainticketapp.repositories;

import com.example.trainticketapp.pojos.entities.SeatEntity;
import com.example.trainticketapp.pojos.projection.SeatUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author ashutosh
 * date 06/02/25
 */
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    SeatEntity findTopByIsTakenFalse();

    List<SeatEntity> findAllBySection(String section);

    @Query(value = "SELECT s.seatId as seatId , s.seatNumber as seatNumber, s.section as section, s.isTaken as isTaken , t.isEnabled as IsEnabled" +
            " ,t.id as ticketId , u.email as email , u.firstName as firstName , u.lastName as lastName , u.id as userId  FROM SeatEntity s " +
            " LEFT JOIN TicketEntity t ON t.seatId = s.seatId" +
            " LEFT JOIN UserEntity u on u.id = t.userId" +
            " WHERE s.section = ?1 ")
    List<SeatUserProjection> findAllTicketAndUserBySection(String sectionName);

    Optional<SeatEntity> findBySeatNumberAndSectionAndIsTakenFalse(Integer seatNumber, String section);
}
