package com.xdstudios.userclientserver;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserDto {

    private Long id;
    private String username;
    private String password;
}
