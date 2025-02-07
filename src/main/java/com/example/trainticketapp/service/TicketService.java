package com.example.trainticketapp.service;

import com.example.trainticketapp.dao.ISeatDao;
import com.example.trainticketapp.dao.ITicketDao;
import com.example.trainticketapp.dao.IUserDao;
import com.example.trainticketapp.exceptions.TicketNotFoundException;
import com.example.trainticketapp.pojos.communicators.request.BookTicketRequest;
import com.example.trainticketapp.pojos.communicators.request.UpdateTicketRequest;
import com.example.trainticketapp.pojos.communicators.response.BookTicketResponse;
import com.example.trainticketapp.pojos.communicators.response.FetchAllSectionResponse;
import com.example.trainticketapp.pojos.communicators.response.FetchTicketReceiptResponse;
import com.example.trainticketapp.pojos.context.SeatContext;
import com.example.trainticketapp.pojos.context.TicketContext;
import com.example.trainticketapp.pojos.context.UserContext;
import com.example.trainticketapp.pojos.entities.SeatEntity;
import com.example.trainticketapp.pojos.entities.TicketEntity;
import com.example.trainticketapp.pojos.entities.UserEntity;
import com.example.trainticketapp.pojos.projection.SeatUserProjection;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ashutosh
 * date 06/02/25
 */

@Service
@Slf4j
public class TicketService implements ITicketService {
    private final IUserDao userDao;
    private final ISeatDao seatDao;
    private final ITicketDao ticketDao;

    public TicketService(IUserDao userDao, ISeatDao seatDao, ITicketDao ticketDao) {
        this.userDao = userDao;
        this.seatDao = seatDao;
        this.ticketDao = ticketDao;
    }

    @Override
    @Transactional
    public BookTicketResponse bookTicket(BookTicketRequest request) throws TicketNotFoundException {

        SeatEntity seatEntity = seatDao.fetchSeatIfAvailable();
        if (seatEntity == null) {
            throw new TicketNotFoundException("No empty seats found");
        }

        UserEntity userEntity = fetchUserEntityForBookingTicket(request);

        TicketEntity ticketEntity = TicketEntity.builder()
                .seatId(seatEntity.getSeatId())
                .id(UUID.randomUUID())
                .userId(userEntity.getId())
                .pricePaid(request.getPricePaid())
                .from(request.getFrom())
                .to(request.getTo())
                .isEnabled(true)
                .build();

        //Save the ticket
        ticketDao.save(ticketEntity);

        seatEntity.setIsTaken(true);
        //Update the ticket as taken
        seatDao.save(seatEntity);

        return BookTicketResponse.builder()
                .userId(userEntity.getId())
                .seatNumber(seatEntity.getSeatNumber())
                .section(seatEntity.getSection())
                .ticketId(ticketEntity.getId())
                .from(ticketEntity.getFrom())
                .to(ticketEntity.getTo())
                .pricePaid(ticketEntity.getPricePaid())
                .build();
    }

    @Override
    public FetchTicketReceiptResponse viewUserTicketReceipt(UUID userId) throws TicketNotFoundException {
        //check if user exists
        userDao.findById(userId);

        List<TicketEntity> tickets = ticketDao.findAllTicketsForUser(userId);
        Map<Long, SeatEntity> seatIdMap = seatDao.findAllById(tickets
                        .stream()
                        .map(TicketEntity::getSeatId).collect(Collectors.toList())).stream()
                .collect(Collectors.toMap(SeatEntity::getSeatId, Function.identity()));


        return FetchTicketReceiptResponse.builder()
                .userId(userId)
                .ticketList(tickets.stream().map(ticket -> {
                    SeatEntity seat = seatIdMap.get(ticket.getSeatId());
                    return TicketContext.builder()
                            .ticketId(ticket.getId())
                            .section(seat.getSection())
                            .seatNumber(seat.getSeatNumber())
                            .amountPaid(ticket.getPricePaid())
                            .build();
                }).collect(Collectors.toList()))
                .build();

    }

    private UserEntity fetchUserEntityForBookingTicket(BookTicketRequest request) throws TicketNotFoundException {
        UserEntity userEntity;
        if (request.getUserId() != null) {
            log.info("Fetching user with Id : {}", request.getUserId());
            userEntity = userDao.findById(request.getUserId());
        } else {

            if (userDao.findByEmail(request.getUserInfo().getEmail()).isPresent()) {
                throw new TicketNotFoundException(
                        String.format(
                                "User with email %s already exist", request.getUserInfo().getEmail()
                        )
                );
            }

            userEntity = userDao.save(UserEntity.builder()
                    .id(UUID.randomUUID())
                    .lastName(request.getUserInfo().getLastName())
                    .firstName(request.getUserInfo().getFirstName())
                    .email(request.getUserInfo().getEmail())
                    .build());

            log.info("Created new user with id : {}", userEntity.getId());
        }
        return userEntity;
    }

    @Override
    @Transactional
    public Boolean updateTicket(UpdateTicketRequest request) throws TicketNotFoundException {
        TicketEntity ticketEntity = ticketDao.findById(request.getTicketId());
        SeatEntity bookedSeat = seatDao.findById(ticketEntity.getSeatId());

        if (Boolean.FALSE.equals(request.getIsEnabled())) {
            log.info("Deleting ticket with Id : {}", request.getTicketId());

            bookedSeat.setIsTaken(false);

            seatDao.saveAll(List.of(bookedSeat));
            ticketDao.deleteAll(List.of(ticketEntity));
        } else {
            log.info("Updating ticket with : {}", request);
            SeatEntity newSeat = seatDao
                    .findBySeatNumberAndSection(request.getNewSeatNumber(), request.getNewSection());

            bookedSeat.setIsTaken(false);
            newSeat.setIsTaken(true);

            ticketEntity.setSeatId(newSeat.getSeatId());

            seatDao.saveAll(List.of(newSeat, bookedSeat));
            ticketDao.save(ticketEntity);
        }

        return true;
    }


    @Override
    @Transactional
    public Boolean removeUserFromTrain(UUID userId) {
        List<TicketEntity> tickets = ticketDao.findAllTicketsForUser(userId);

        List<Long> seatId = tickets.stream().map(t -> t.getSeatId()).collect(Collectors.toList());

        List<SeatEntity> seats = seatDao.findAllById(seatId);

        seats.forEach(s -> s.setIsTaken(false));

        seatDao.saveAll(seats);

        ticketDao.deleteAll(tickets);

        return true;
    }

    @Override
    public FetchAllSectionResponse viewAllTicketForAllSection(String section) {
        List<SeatUserProjection> allTicketsBySection = seatDao.findAllTicketsBySection(section);

        return FetchAllSectionResponse.builder()
                .seats(allTicketsBySection.stream()
                        .map(s -> SeatContext.builder()
                                .isTaken(s.getIsTaken())
                                .seatId(s.getSeatId())
                                .seatNumber(s.getSeatNumber())
                                .userInfo(s.getIsTaken() ? UserContext.builder()
                                        .userId(s.getUserId())
                                        .firstName(s.getFirstName())
                                        .lastName(s.getLastName())
                                        .email(s.getEmail())
                                        .build() : null)
                                .ticketId(s.getIsTaken() ? s.getTicketId() : null)
                                .section(s.getSection())
                                .build()).collect(Collectors.toList()))
                .build();

    }
}
