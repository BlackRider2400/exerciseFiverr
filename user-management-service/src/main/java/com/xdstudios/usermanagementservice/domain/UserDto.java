package com.xdstudios.usermanagementservice.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
}
