package com.ithuhu.pojo.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    Long id;
    String title;
    String content;
    Long userId;
    String author;
    Long categoryId;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
