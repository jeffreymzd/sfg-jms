package com.github.jeffrey.spring.boot.sfg.jms.jms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jeffrey.spring.boot.sfg.jms.config.JmsConfig;
import com.github.jeffrey.spring.boot.sfg.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jgroups.util.UUID;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.time.LocalDateTime;

/**
 * Created by jeffreymzd on 3/22/20
 */
@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {

        System.out.println("I'm sending a message");

        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World! @" + LocalDateTime.now())
                .build();

        jmsTemplate.convertAndSend(JmsConfig.HELLO_WORLD_QUEUE, message);

        System.out.println("Message sent!");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        System.out.println("I'm sending a message");

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.SEND_RECEIVE_QUEUE, new MessageCreator() {
            @SneakyThrows
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(objectMapper
                            .writeValueAsString(HelloWorldMessage.builder()
                                    .id(UUID.randomUUID())
                                    .message("Hi")
                                    .build()));
                    System.out.println("Sending Hi");
                    helloMessage.setStringProperty("_type", "com.github.jeffrey.spring.boot.sfg.jms.model.HelloWorldMessage");
                } catch (JsonProcessingException e) {
                    throw new JMSException(e.getMessage());
                }

                return helloMessage;
            }
        });

        System.out.println("Received back: " + receivedMsg.getBody(String.class));
    }
}
