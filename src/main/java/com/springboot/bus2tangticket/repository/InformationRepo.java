package com.springboot.bus2tangticket.repository;

import com.springboot.bus2tangticket.model.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepo extends JpaRepository<Information, Integer> {
    boolean existsByCic(String cic);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
