package com.demo.backend.repository;

import com.demo.backend.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByAddresses_CountryEquals(String country);
}
