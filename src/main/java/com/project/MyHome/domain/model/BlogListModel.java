package com.project.MyHome.domain.model;

import lombok.Data;

@Data
public class BlogListModel {
    long id;

    String title;

    String content;

    String imageName;

    String fileName;

    String categoryName;

    String createDate;

    String updateDate;
}
