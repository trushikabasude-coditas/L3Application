package com.example.L3Application.repo;

import com.example.L3Application.entity.Appointment;
import com.example.L3Application.entity.QueueToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QueueTokenRepository extends JpaRepository<QueueToken,Long>{
    Optional<QueueToken> findByAppointment(Appointment appointment);
    List<QueueToken> findByIssueDateOrderByTokenNumberAsc(LocalDate issueDate);
}
