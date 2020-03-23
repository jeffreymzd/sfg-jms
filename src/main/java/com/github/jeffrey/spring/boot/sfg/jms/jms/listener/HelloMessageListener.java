package com.github.jeffrey.spring.boot.sfg.jms.jms.listener;

import com.github.jeffrey.spring.boot.sfg.jms.config.JmsConfig;
import com.github.jeffrey.spring.boot.sfg.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.jgroups.util.UUID;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by jeffreymzd on 3/22/20
 */
@RequiredArgsConstructor
@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.HELLO_WORLD_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       Message message) {
//        System.out.println("I got a message!");
//        System.out.println(helloWorldMessage);
//        System.out.println("Message consumed!");
    }

    @JmsListener(destination = JmsConfig.SEND_RECEIVE_QUEUE)
    public void listenForHi(@Payload HelloWorldMessage helloWorldMessage,
                            @Headers MessageHeaders headers,
                            Message message) throws JMSException {
        HelloWorldMessage replyPayload = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hi back!")
                .build();
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), replyPayload);
    }
}
