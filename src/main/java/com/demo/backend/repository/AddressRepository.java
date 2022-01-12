package com.demo.backend.repository;

import com.demo.backend.domain.Address;
import com.demo.backend.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    public List<Address> findByCountry(String country);
}
