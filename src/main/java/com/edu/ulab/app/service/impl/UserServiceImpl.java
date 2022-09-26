package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);
        Person savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);
        return userMapper.personToUserDto(savedUser);
    }

    @Override
    public void updateUser(UserDto userDto) {
        // реализовать недстающие методы
        if (!userRepository.existsById(userDto.getId())) {
            throw new NotFoundException("User with the specified ID does not exist.");
        }
        Person person = userMapper.userDtoToPerson(userDto);
        userRepository.save(person);
    }

    @Override
    public UserDto getUserById(Long id) {
        // реализовать недстающие методы
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with the specified ID does not exist.");
        }
        Person person = userRepository.findById(id).get();
        return userMapper.personToUserDto(person);
    }

    @Override
    public void deleteUserById(Long id) {
        // реализовать недстающие методы
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with the specified ID does not exist.");
        }
        userRepository.deleteById(id);
    }
}
