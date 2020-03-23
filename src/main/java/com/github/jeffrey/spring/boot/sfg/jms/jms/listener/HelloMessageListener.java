package com.github.jeffrey.spring.boot.sfg.jms.jms.listener;

import com.github.jeffrey.spring.boot.sfg.jms.config.JmsConfig;
import com.github.jeffrey.spring.boot.sfg.jms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

/**
 * Created by jeffreymzd on 3/22/20
 */
@Component
public class HelloMessageListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       Message message) {
        System.out.println("I got a message!");
        System.out.println(helloWorldMessage);
        System.out.println("Message consumed!");
    }
}
