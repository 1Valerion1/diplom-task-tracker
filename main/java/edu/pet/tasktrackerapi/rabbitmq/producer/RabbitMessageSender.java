package edu.pet.tasktrackerapi.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.pet.tasktrackerapi.rabbitmq.dto.EmailDto;
import edu.pet.tasktrackerapi.rabbitmq.service.RabbitMessageCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.ConnectException;

@Component
@RequiredArgsConstructor
public class RabbitMessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMessageCreator rabbitMessageCreator;
    private final ObjectMapper objectMapper;

    public void sendWelcomeEmail(String receiverEmail) throws JsonProcessingException {
        String queueName = QueueName.EMAIL_SENDER_TASKS.toString();
        EmailDto emailDto = rabbitMessageCreator.createWelcomeMessage(receiverEmail);
        try {
            rabbitTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(emailDto));
        }catch (Exception e){
            //TODO log ex when unable to connect service
            //handle exception in controlleradvice analog????
        }


    }


}
