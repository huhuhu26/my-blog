package com.ithuhu.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryParam {
    private int page = 1;
    private int pageSize = 10;
    private boolean onlyMine = false;
    private String keyword = "";// 搜索关键字
    private Integer categoryId;
}
