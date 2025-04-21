package com.springboot.bus2tangticket.repository;

import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    boolean existsByAccountName(String accountName);
}
