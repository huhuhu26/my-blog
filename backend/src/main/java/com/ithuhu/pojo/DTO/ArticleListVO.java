package com.ithuhu.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVO {
    Long id;
    String title;
    Integer userId;
    String author;//作者
    Long categoryId;
    String category;//分类
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
