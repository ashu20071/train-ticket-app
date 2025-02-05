package com.example.trainticketapp.dao;

import com.example.trainticketapp.exceptions.TicketNotFoundException;
import com.example.trainticketapp.pojos.entities.SeatEntity;
import com.example.trainticketapp.pojos.projection.SeatUserProjection;

import java.util.List;

/**
 * @author ashutosh
 * date 06/02/25
 */
public interface ISeatDao {
    List<SeatEntity> saveAll(List<SeatEntity> seatEntities);

    SeatEntity save(SeatEntity seatEntity);

    SeatEntity fetchSeatIfAvailable();

    List<SeatEntity> findAllById(List<Long> seatId);

    SeatEntity findById(Long seatId) throws TicketNotFoundException;

    List<SeatUserProjection> findAllBySection(String section);

    SeatEntity findBySeatNumberAndSection(Integer seatNumber, String section) throws TicketNotFoundException;
}
