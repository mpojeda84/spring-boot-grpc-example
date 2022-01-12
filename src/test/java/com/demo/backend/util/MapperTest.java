package com.demo.backend.util;

import com.demo.backend.domain.Address;
import com.demo.backend.domain.User;
import com.demo.backend.dto.AddressDto;
import com.demo.backend.dto.UserDto;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    private User getUser(){

        User user = new User("Maikel", "Pereira", "maikel@gmail.com",
                "abc123", null);
        Address address = new Address("a1","a2", "Miami", "FL",
                "33066", "USA" , user);

        user.setAddresses(Set.of(address));

        return user;
    }

    private UserDto getUserDto(){
        UserDto dto = new UserDto(1, "Maikel", "Pereira", "maikel@gmail.com",
                "abc123", Set.of(new AddressDto("a1","a2", "Miami", "FL",
                "33066", "USA" )));
        return dto;
    }

    @Test
    void toUser() {
        User user = Mapper.getInstance().toUser(getUserDto());
        assertEquals(user.hashCode(), getUser().hashCode());
    }

    @Test
    void toAddresses() {
        Address address = Mapper.getInstance().toAddresses(getUserDto().getAddresses(), getUser()).stream().findFirst().get();
        assertEquals(getUser().getAddresses().stream().findFirst().get(), address);
    }

    @Test
    void toUserDto() {
        // you get the idea
    }

    @Test
    void toAddressDtos() {
        // you get the idea
    }

    @Test
    void toUserResponse() {
        // you get the idea
    }
}