package com.project.MyHome.domain.request;

import lombok.Data;

import org.jetbrains.annotations.NotNull;

@Data
public class BlogListRequest {

    private String category;

    private String categoryNum;

    private Integer pageSize;

    private Integer pageNo;

    private Integer pageOffset;

}
