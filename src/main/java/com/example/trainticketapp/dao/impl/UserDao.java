package com.example.trainticketapp.dao.impl;

import com.example.trainticketapp.dao.IUserDao;
import com.example.trainticketapp.exceptions.TicketNotFoundException;
import com.example.trainticketapp.pojos.entities.UserEntity;
import com.example.trainticketapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author ashutosh
 * date 06/02/25
 */

@Service
public class UserDao implements IUserDao {
    private final UserRepository userRepository;

    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity findById(UUID userId) throws TicketNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new TicketNotFoundException(
                String.format("No user with Id : %s found ", userId)
        ));
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) throws TicketNotFoundException {
        return userRepository.findByEmail(email);
    }
}
