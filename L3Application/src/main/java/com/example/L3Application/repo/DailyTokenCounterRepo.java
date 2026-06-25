package com.example.L3Application.repo;

import com.example.L3Application.entity.DailyTokenCounter;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyTokenCounterRepo extends JpaRepository<DailyTokenCounter,Long> {
@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from DailyTokenCounter c where c.issueDate =:date")
Optional<DailyTokenCounter> findByIdForUpdate(@Param("date") LocalDate date);

}
