package com.github.jeffrey.spring.boot.sfg.jms.jms.sender;

import com.github.jeffrey.spring.boot.sfg.jms.config.JmsConfig;
import com.github.jeffrey.spring.boot.sfg.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.jgroups.util.UUID;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jeffreymzd on 3/22/20
 */
@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {

        System.out.println("I'm sending a message");

        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World! @" + LocalDateTime.now())
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        System.out.println("Message sent!");
    }
}
