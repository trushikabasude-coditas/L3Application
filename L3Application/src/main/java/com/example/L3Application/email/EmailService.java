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
    public void sendBookingConfirmation(String to, String name, LocalDate date, LocalTime time) {
        send(to, "Your appointment is booked",
                "Hi " + name + ",\n\nYour appointment is confirmed for " + date + " at " + time +
                        ".\nPlease complete your intake before you arrive.\n\n— Threshold Clinic");
    }

    @Async
    public void sendIntakeReminder(String to, String name, LocalDate date, LocalTime time) {
        send(to, "Please finish your intake",
                "Hi " + name + ",\n\nYour appointment on " + date + " at " + time +
                        " is coming up and your intake isn't complete. Please finish it so check-in is quick.\n\n— Threshold Clinic");
    }

    @Async
    public void sendSummaryReady(String to, String name) {
        send(to, "Your visit summary is ready!!",
                "Hi" +name+",\nYour clinician has added a summary of your visit Please have a look."+                 "You can read and download it from your account.\n\n— Threshold Clinic");
    }private void send(String to, String subject, String body) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
            log.info("Email '{}' sent to {}", subject, to);
        } catch (Exception e) {
            log.warn("Email '{}' to {} failed (continuing): {}", subject, to, e.getMessage());
        }
    }
}
