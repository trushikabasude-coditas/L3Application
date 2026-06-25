package com.example.L3Application.service;

import com.example.L3Application.dto.response.QueuePositionResponseDto;
import com.example.L3Application.entity.Appointment;
import com.example.L3Application.entity.DailyTokenCounter;
import com.example.L3Application.entity.QueueToken;
import com.example.L3Application.enums.AppointmentStatus;
import com.example.L3Application.repo.DailyTokenCounterRepo;
import com.example.L3Application.repo.QueueTokenRepo;
import com.example.L3Application.repo.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class QueueService {
    private  final QueueTokenRepository repo;
private  final DailyTokenCounterRepo dailyTokenCounterRepo;

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
    @Transactional(readOnly = true)
    public QueuePositionResponseDto positionFor(Appointment appointment) {
        QueueToken me=repo.findByAppointment(appointment).orElse(null);
        if (me==null)
            return new QueuePositionResponseDto(null, null, 0);

        List<QueueToken> waiting=repo.findByIssueDateOrderByTokenNumberAsc(LocalDate.now()).stream()

                .filter(token->token.getAppointment().getAppointmentStatus()==AppointmentStatus.CHECKED_IN)
                .toList();
        int position=0;
 for(int i=0;i<waiting.size();i++) {
            if(waiting.get(i).getId().equals(me.getId()))
            {
                position = i+1;
                   break;
            }
        }
        return new QueuePositionResponseDto(me.getTokenNumber(),position,waiting.size());

    }


}
