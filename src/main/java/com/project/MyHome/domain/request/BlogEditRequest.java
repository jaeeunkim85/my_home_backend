package com.project.MyHome.domain.request;

import lombok.Data;

@Data
public class BlogEditRequest {
    long id;

    String title;

    String content;

    String category;

    int categoryNum;

    String imageName;

    String fileName;

    String thumImage;
}
