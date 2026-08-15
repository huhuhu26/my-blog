package com.ithuhu.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String avatar;
    private LocalDateTime createdAt;
    private String token;
}
