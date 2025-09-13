package com.ganatan.backend_java.modules.users.services;

import com.ganatan.backend_java.modules.users.entities.User;
import com.ganatan.backend_java.modules.users.repository.UserRepository;
import com.ganatan.backend_java.modules.users.user.UserDTO;
import com.ganatan.backend_java.modules.users.user.UserDTOPut;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public UserDTO getModelById(Long id) {
        return new UserDTO(this.getById(id));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public UserDTO getModelByEmail(String email) {
        return new UserDTO(this.getByEmail(email));
    }
}
