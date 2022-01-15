package com.xdstudios.usermanagementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MessageMQ {

    private String messageId;
    private String message;
    private List<UserDto> userDtoList;
}
