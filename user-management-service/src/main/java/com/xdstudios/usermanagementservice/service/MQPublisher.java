package com.xdstudios.usermanagementservice.service;

import com.xdstudios.usermanagementservice.config.MQConfig;
import com.xdstudios.usermanagementservice.domain.MessageMQ;
import com.xdstudios.usermanagementservice.mapper.UserMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MQPublisher {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DbService dbService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendToQueue(){
        MessageMQ message = new MessageMQ(UUID.randomUUID().toString(), "LIST", userMapper.mapToUserDtoList(dbService.getAllUsers()));
        rabbitTemplate.convertAndSend(MQConfig.QUEUE_MAIN_TO_READ, message);
    }
}
