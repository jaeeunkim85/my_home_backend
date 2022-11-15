package com.project.MyHome.domain.request;

import lombok.Data;

@Data
public class BlogCreateRequest {
    long id;

    String title;

    String content;

    String category;

    int categoryNum;

    String imageName;

    String fileName;
}
