package edu.pet.tasktrackerapi.rabbitmq.service;

import edu.pet.tasktrackerapi.rabbitmq.dto.EmailDto;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageCreator {
    public EmailDto createWelcomeMessage(String receiverEmail) {
        EmailDto emailDto = new EmailDto();
        emailDto.setReceiverEmail(receiverEmail);
        emailDto.setSubject("WELCOME");
        emailDto.setBody("WELCOME");

        return emailDto;
    }
}
