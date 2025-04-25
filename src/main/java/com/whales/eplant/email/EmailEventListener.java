package com.whales.eplant.email;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailEventListener {
    private final EmailService emailService;

    @Async
    @EventListener
    public void handleEmailEvent(EmailEvent event) {
        emailService.sendEmail(event.getTo(), event.getSubject(), event.getText());
    }

    @Override
    public String toString() {
        return "EmailEventListener{" +
                "emailService=" + emailService +
                '}';
    }
}
