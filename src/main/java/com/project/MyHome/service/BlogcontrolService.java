package com.project.MyHome.service;


import com.project.MyHome.domain.model.Category;
import com.project.MyHome.domain.request.BlogCreateRequest;
import com.project.MyHome.domain.request.BlogEditRequest;
import com.project.MyHome.domain.request.BlogListRequest;
import com.project.MyHome.mapper.BlogcontentMapper;
import com.project.MyHome.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BlogcontrolService {

    @Autowired
    BlogcontentMapper blogcontentMapper;

    @Autowired
    FileSaverService fileSaverService;

    public Response getCategory() {
        Response result = Response.from();
        List<Category> categoryList =  blogcontentMapper.getCategoryList();
        result.setData(categoryList);
        return result;
    }

    public Response createBolog(BlogCreateRequest blogCreateRequest, MultipartFile[] files) throws Exception{
        Response result = Response.from();
        if (files != null && files.length > 0) {
            fileSaverService.uploadFiles(files);
        }
        blogCreateRequest.setCategoryNum(blogcontentMapper.getCategoryNum(blogCreateRequest.getCategory()));
        log.error("cagegory numnber " + blogCreateRequest.getCategoryNum());
        blogcontentMapper.createBlog(blogCreateRequest);
        result.setData(true);
        return result;

    }

    public Response getBlogList(HttpServletRequest request) {
        Response result = Response.from();

        result.setData(blogcontentMapper.getBlogList(getPageParameter(request)));

        return result;
    }

    private BlogListRequest getPageParameter(HttpServletRequest request) {
        BlogListRequest blogListRequest = new BlogListRequest();
        blogListRequest.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
        blogListRequest.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        blogListRequest.setCategory(request.getParameter("category"));
        int offset = (blogListRequest.getPageNo() - 1) * blogListRequest.getPageSize();
        blogListRequest.setPageOffset(offset);
        log.error("category = " + blogListRequest.getCategory());

        return blogListRequest;
    }

    public Response getBlogDetail(long blogid) {
        Response result = Response.from();
        result.setData(blogcontentMapper.getBlogDetail(blogid));
        return result;
    }

    public Response blogDetailUpdate(BlogEditRequest blogEditRequest, MultipartFile[] files) throws Exception{
        Response result = Response.from();
        blogEditRequest.setCategoryNum(blogcontentMapper.getCategoryNum(blogEditRequest.getCategory()));
        blogcontentMapper.blogDetailUpdate(blogEditRequest);
        result.setData(true);
        return result;
    }
}
