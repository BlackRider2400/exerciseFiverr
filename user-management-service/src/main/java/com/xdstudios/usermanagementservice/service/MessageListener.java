package com.xdstudios.usermanagementservice.service;

import com.xdstudios.usermanagementservice.config.MQConfig;
import com.xdstudios.usermanagementservice.domain.MessageMQ;
import com.xdstudios.usermanagementservice.domain.UserDto;
import com.xdstudios.usermanagementservice.mapper.UserMapper;
import com.xdstudios.usermanagementservice.service.MQPublisher;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MessageListener {

    @Autowired
    private MQPublisher publisher;

    @Autowired
    private DbService dbService;

    @Autowired
    private UserMapper userMapper;

    @RabbitListener(queues = {MQConfig.QUEUE_READ_TO_MAIN, MQConfig.QUEUE_FROM_CLIENT} )
    public void listener(MessageMQ message){

        log.warn("Queue message: " + message.toString());
        if (message.getMessage().equals("READ")){
            publisher.sendToQueue();
        }
        else if(message.getMessage().equals("CREATE")){
             dbService.saveUser(userMapper.mapToUser(message.getUserDtoList().get(0)));
             log.info("Saved user: " + message.getUserDtoList().get(0).getUsername());
        }
        else if(message.getMessage().equals("UPDATE")){
            UserDto userDto = message.getUserDtoList().get(0);
            if(dbService.checkIfExists(userDto.getId())){
                dbService.deleteUser(userDto.getId());
                dbService.saveUser(userMapper.mapToUser(userDto));
                log.info("Updated user: " + userDto.getUsername());
            }else{
                log.warn("There is no such a user.");
            }
        }
    }


}
