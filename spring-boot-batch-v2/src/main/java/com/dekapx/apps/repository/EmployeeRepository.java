package com.dekapx.apps.repository;

import org.springframework.stereotype.Repository;
import com.dekapx.apps.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Object findByRedisId(String redisId);
}
