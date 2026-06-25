package com.example.L3Application.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    @Async
    public void sendBookingConfirmation(String to,String name,LocalDate date,LocalTime time){
        send(to, "Your appointment is booked","Hi"+name+"\nYour appointment is confirmed for"+date+"and at"+time+                        ".\nPlease complete your intake before you arrive.\n\n— Threshold Clinic");
    }
    private void send(String to,String subject,String body){
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
            log.info("Email '{}' sent to {}",subject,to);
        } catch(Exception e){
            log.warn("Email'{}'to {} failed:{}", subject,to,e.getMessage());
        }
    }
}
