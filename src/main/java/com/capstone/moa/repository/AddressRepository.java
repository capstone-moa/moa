package com.capstone.moa.repository;

import com.capstone.moa.entity.Address;
import com.capstone.moa.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByGroup(Group group);

    boolean existsByGroup(Group group);
}
