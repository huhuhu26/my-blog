package com.ithuhu.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVO {
    Long id;
    String title;
    String content;
    Long userId;
    String author;
    Long categoryId;
    String category;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
