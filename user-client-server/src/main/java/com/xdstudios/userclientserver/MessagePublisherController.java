package com.xdstudios.userclientserver;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class MessagePublisherController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private boolean temp = true;
    private List<UserDto> userDtoList;

    @PostMapping("/createUser")
    public void createUser(@RequestBody UserDto userDto){
        log.info("Sending creation info, " + userDto.toString());
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);
        MessageMQ messageMQ = new MessageMQ(UUID.randomUUID().toString(), "CREATE", userDtoList);
        rabbitTemplate.convertAndSend(MQConfig.QUEUE_TO_MANAGEMENT, messageMQ);
    }

    @PostMapping("/updateUser")
    public void updateUser(@RequestBody UserDto userDto){
        log.info("Sending update info, " + userDto.toString());
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);
        MessageMQ messageMQ = new MessageMQ(UUID.randomUUID().toString(), "UPDATE", userDtoList);
        rabbitTemplate.convertAndSend(MQConfig.QUEUE_TO_MANAGEMENT, messageMQ);
    }

    @GetMapping("/getUsers")
    public List<UserDto> getUsers(){
        rabbitTemplate.convertAndSend(MQConfig.QUEUE_TO_READ, new MessageMQ(UUID.randomUUID().toString(), "READ", null));

        return userDtoList;
    }

    @RabbitListener(queues = MQConfig.QUEUE_FROM_READ)
    public void changeTemp(MessageMQ messageMQ){
        userDtoList = messageMQ.getUserDtoList();
    }
}
