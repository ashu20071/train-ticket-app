package com.example.trainticketapp.dao;

import com.example.trainticketapp.exceptions.TicketNotFoundException;
import com.example.trainticketapp.pojos.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * @author ashutosh
 * date 06/02/25
 */
public interface IUserDao {
    UserEntity save(UserEntity userEntity);

    UserEntity findById(UUID userId) throws TicketNotFoundException;

    Optional<UserEntity> findByEmail(String email) throws TicketNotFoundException;
}
