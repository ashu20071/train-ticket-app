package com.example.trainticketapp.dao.impl;

import com.example.trainticketapp.dao.ISeatDao;
import com.example.trainticketapp.exceptions.TicketNotFoundException;
import com.example.trainticketapp.pojos.entities.SeatEntity;
import com.example.trainticketapp.pojos.projection.SeatUserProjection;
import com.example.trainticketapp.repositories.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ashutosh
 * date 06/02/25
 */

@Service
public class SeatDao implements ISeatDao {
    private final SeatRepository seatRepository;

    public SeatDao(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<SeatEntity> saveAll(List<SeatEntity> seatEntities) {
        return seatRepository.saveAll(seatEntities);
    }

    @Override
    public SeatEntity save(SeatEntity seatEntity) {
        return seatRepository.save(seatEntity);
    }

    @Override
    public SeatEntity fetchSeatIfAvailable() {
        return seatRepository.findTopByIsTakenFalse();
    }

    @Override
    public List<SeatEntity> findAllById(List<Long> seatId) {
        return seatRepository.findAllById(seatId);
    }

    @Override
    public SeatEntity findById(Long seatId) throws TicketNotFoundException {
        return seatRepository.findById(seatId).orElseThrow(() -> new TicketNotFoundException(
                String.format("Seat with Id: %s not found", seatId)
        ));
    }

    @Override
    public List<SeatUserProjection> findAllBySection(String section) {
        return seatRepository.findAllTicketAndUserBySection(section);
    }

    @Override
    public SeatEntity findBySeatNumberAndSection(Integer seatNumber, String section) throws TicketNotFoundException {
        return seatRepository.findBySeatNumberAndSectionAndIsTakenFalse(seatNumber, section).orElseThrow(() -> new TicketNotFoundException(
                String.format("No ticket with seat number : %s and section : %s ", seatNumber, seatNumber)
        ));
    }
}
