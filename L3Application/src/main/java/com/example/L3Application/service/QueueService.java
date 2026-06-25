package com.example.L3Application.service;

import com.example.L3Application.entity.Appointment;
import com.example.L3Application.entity.DailyTokenCounter;
import com.example.L3Application.entity.QueueToken;
import com.example.L3Application.repo.DailyTokenCounterRepo;
import com.example.L3Application.repo.QueueTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class QueueService {
    private QueueTokenRepo repo;
private DailyTokenCounterRepo dailyTokenCounterRepo;
    @Transactional
    public QueueToken issueToken(Appointment appointment) {
        return repo.findByAppointment(appointment).orElseGet(() -> {
            LocalDate today = LocalDate.now();
            DailyTokenCounter counter = dailyTokenCounterRepo.findByIdForUpdate(today).orElseGet(() -> {
                dailyTokenCounterRepo.saveAndFlush(DailyTokenCounter.builder().issueDate(today).lastToken(0).build());
                return dailyTokenCounterRepo.findByIdForUpdate(today).orElseThrow();
            });
            counter.setLastToken(counter.getLastToken() + 1);
            return repo.save(QueueToken.builder()
                    .tokenNumber(counter.getLastToken())
                    .issueDate(today)
                    .appointment(appointment)
                    .issuedAt(LocalDateTime.now())
                    .build());
        });
    }

}
