package com.example.trainticketapp.service;

import com.example.trainticketapp.dao.ISeatDao;
import com.example.trainticketapp.dao.IUserDao;
import com.example.trainticketapp.pojos.entities.SeatEntity;
import com.example.trainticketapp.pojos.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * @author ashutosh
 * date 07/02/25
 */

@Slf4j
@Component
public class DataInitializer implements ApplicationRunner {
    private final IUserDao userDao;

    private final ISeatDao seatDao;

    private static final String SECTION_A = "A";
    private static final String SECTION_B = "B";

    public DataInitializer(IUserDao userDao, ISeatDao seatDao) {
        this.userDao = userDao;
        this.seatDao = seatDao;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        userDao.save(UserEntity.builder()
                .id(UUID.randomUUID())
                .email("ashutosh.singh@gmail.com")
                .firstName("Ashutosh")
                .lastName("Singh")
                .build());

        userDao.save(UserEntity.builder()
                .id(UUID.randomUUID())
                .email("user@gmail.com")
                .firstName("User")
                .lastName("One")
                .build());

        log.info("User data created");

        List<SeatEntity> seats = List.of(

                SeatEntity.builder()
                        .section(SECTION_A)
                        .seatNumber(1)
                        .isTaken(false)
                        .build(),

                SeatEntity.builder()
                        .section(SECTION_A)
                        .seatNumber(2)
                        .isTaken(false)
                        .build(),

                SeatEntity.builder()
                        .section(SECTION_A)
                        .seatNumber(3)
                        .isTaken(false)
                        .build(),

                SeatEntity.builder()
                        .section(SECTION_A)
                        .seatNumber(4)
                        .isTaken(false)
                        .build(),
                SeatEntity.builder()
                        .section(SECTION_B)
                        .seatNumber(1)
                        .isTaken(false)
                        .build(),
                SeatEntity.builder()
                        .section(SECTION_B)
                        .seatNumber(2)
                        .isTaken(false)
                        .build(),

                SeatEntity.builder()
                        .section(SECTION_B)
                        .seatNumber(3)
                        .isTaken(false)
                        .build(),

                SeatEntity.builder()
                        .section(SECTION_B)
                        .seatNumber(4)
                        .isTaken(false)
                        .build()
        );

        seatDao.saveAll(seats);

        log.info("Seat data created");

    }
}
