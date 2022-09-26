package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImplTemplate implements UserService {
    private final JdbcTemplate jdbcTemplate;

    public UserServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        final String INSERT_SQL = "INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return userDto;
    }

    @Override
    public void updateUser(UserDto userDto) {
        // реализовать недстающие методы
        if (!isExist(userDto.getId())) {
            throw new NotFoundException("User with the specified ID does not exist.");
        }
        jdbcTemplate.update("UPDATE PERSON SET FULL_NAME=?, TITLE=?, AGE=? WHERE ID=?",
                new Object[]{userDto.getFullName(), userDto.getTitle(), userDto.getAge(), userDto.getId()});
    }

    @Override
    public UserDto getUserById(Long id) {
        // реализовать недстающие методы
        if (!isExist(id)) {
            throw new NotFoundException("User with the specified ID does not exist.");
        }
        UserDto userDto = jdbcTemplate.queryForObject("SELECT * FROM PERSON WHERE ID=?",
                BeanPropertyRowMapper.newInstance(UserDto.class), id);

        return userDto;
    }

    @Override
    public void deleteUserById(Long id) {
        // реализовать недстающие методы
        if (!isExist(id)) {
            throw new NotFoundException("User with the specified ID does not exist.");
        }
        jdbcTemplate.update("DELETE FROM PERSON WHERE ID=?", id);
    }

    public boolean isExist(Long id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM PERSON WHERE ID=?)", Boolean.class, id));
    }
}
