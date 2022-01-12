package com.demo.backend.rest;

import com.demo.backend.dto.UserDto;
import com.demo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public @ResponseBody UserDto addNewUser (@RequestBody UserDto user) {
        return userService.create(user);
    }

    @GetMapping
    public @ResponseBody Set<UserDto> getByCountry (@RequestParam String country) {return userService.getByCountry(country);}
}
