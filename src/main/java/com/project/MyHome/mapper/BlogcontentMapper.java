package com.project.MyHome.mapper;

import com.project.MyHome.domain.model.BlogListModel;
import com.project.MyHome.domain.model.Category;
import com.project.MyHome.domain.request.BlogCreateRequest;
import com.project.MyHome.domain.request.BlogEditRequest;
import com.project.MyHome.domain.request.BlogListRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogcontentMapper {

    List<Category> getCategoryList();

    int getCategoryNum(String categoryName);

    void createBlog(BlogCreateRequest blogCreateRequest);

    List<BlogListModel> getBlogList(BlogListRequest category);

    BlogListModel getBlogDetail (long blogid);

    void blogDetailUpdate (BlogEditRequest editRequest);
}
