package com.springboot.bus2tangticket.repository;

import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepo extends JpaRepository<Assignment, Integer> {
}
