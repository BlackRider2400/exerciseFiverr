package com.xdstudios.userreadservice;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Log4j2
public class MessageListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = {MQConfig.QUEUE_FROM_CLIENT, MQConfig.QUEUE_FROM_MANAGEMENT})
    public void listener(MessageMQ messageMQ){
        if (messageMQ.getMessage().equals("READ")){
            rabbitTemplate.convertAndSend(MQConfig.QUEUE_TO_MANAGEMENT, new MessageMQ(UUID.randomUUID().toString(), "READ", null));
        }
        else if(messageMQ.getMessage().equals("LIST")){
            rabbitTemplate.convertAndSend(MQConfig.QUEUE_TO_CLIENT, new MessageMQ(UUID.randomUUID().toString(), "LIST", messageMQ.getUserDtoList()));
            log.info("Sending list to client: " + messageMQ.getUserDtoList().stream().map(i -> i.getUsername()).collect(Collectors.toList()));
        }
    }
}
