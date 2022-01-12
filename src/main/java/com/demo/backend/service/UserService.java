package com.demo.backend.service;

import com.demo.backend.dto.UserDto;

import java.util.Set;

public interface UserService {
    public UserDto create(UserDto userDto);
    public Set<UserDto> getByCountry(String country);
}
