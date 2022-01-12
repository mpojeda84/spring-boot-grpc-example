package com.demo.backend.util;

import com.demo.backend.domain.Address;
import com.demo.backend.domain.User;
import com.demo.backend.dto.AddressDto;
import com.demo.backend.dto.UserDto;
import com.demo.backend.service.UserResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {

    private static Mapper mapper;
    private Mapper() {};

    public static Mapper getInstance() {
        if(mapper == null)
            mapper = new Mapper();
        return mapper;
    }

    public User toUser (UserDto userDto) {
        if(userDto == null)
            return null;
        User user = new User(userDto.getFirstName(),userDto.getLastName(),userDto.getEmail(),userDto.getPassword(),null);
        user.setAddresses(this.toAddresses(userDto.getAddresses(), user));
        return user;
    }
    public Set<Address> toAddresses (Set<AddressDto> addressDtos, User user) {
        if (addressDtos == null || user == null)
            return null;

        return addressDtos
                .stream()
                .map(x -> new Address(x.getAddress1(),x.getAddress2(),x.getCity(),x.getState(),x.getZip(),x.getCountry(), user))
                .collect(Collectors.toSet());
    }
    public UserDto toUserDto (User user) {
        if(user == null)
            return null;
        UserDto userDto = new UserDto(user.getId(), user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword(),null);
        userDto.setAddresses(this.toAddressDtos(user.getAddresses()));
        return userDto;
    }
    public Set<AddressDto> toAddressDtos (Set<Address> addresses) {
        if (addresses == null)
            return null;

        return addresses
                .stream()
                .map(x -> new AddressDto(x.getAddress1(),x.getAddress2(),x.getCity(),x.getState(),x.getZip(),x.getCountry()))
                .collect(Collectors.toSet());
    }

    public UserResponse toUserResponse(User user) {
        if(user == null)
            return null;

        UserResponse.Builder userBuilder = UserResponse.newBuilder();
        if (user.getEmail() != null) userBuilder.setEmail(user.getEmail());
        if (user.getFirstName() != null) userBuilder.setFirstName(user.getFirstName());
        if (user.getLastName() != null) userBuilder.setLastName(user.getLastName());
        if (user.getPassword() != null) userBuilder.setPassword(user.getPassword());
        if (user.getId() != null) userBuilder.setId(user.getId().intValue());

        if(user.getAddresses() != null) {
            List<com.demo.backend.service.Address> addresses = user.getAddresses().stream().map(x -> {
                com.demo.backend.service.Address.Builder addressBuilder = com.demo.backend.service.Address.newBuilder();
                if (x.getAddress1() != null) addressBuilder.setAddress1(x.getAddress1());
                if (x.getAddress2() != null) addressBuilder.setAddress2(x.getAddress2());
                if (x.getCity() != null) addressBuilder.setCity(x.getCity());
                if (x.getState() != null) addressBuilder.setState(x.getState());
                if (x.getZip() != null) addressBuilder.setZip(x.getZip());
                if (x.getCountry() != null) addressBuilder.setCountry(x.getCountry());
                return addressBuilder.build();
            }).collect(Collectors.toList());
            userBuilder.addAllAddresses(addresses);
        }

        return userBuilder.build();
    }
}
