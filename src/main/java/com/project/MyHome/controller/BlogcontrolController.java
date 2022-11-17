package com.project.MyHome.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.MyHome.domain.model.BlogListModel;
import com.project.MyHome.domain.request.BlogCreateRequest;
import com.project.MyHome.domain.request.BlogEditRequest;
import com.project.MyHome.domain.request.BlogListRequest;
import com.project.MyHome.service.BlogcontrolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.Callable;

@RestController
@Slf4j
@RequestMapping("/v1/blog")
public class BlogcontrolController {

    @Autowired
    BlogcontrolService blogcontrolService;

    @GetMapping(value = "/category", produces = "application/json;charset=utf-8")
    public Callable<Object> getCategory(final HttpServletRequest request) throws Exception {
        return () -> {
            log.error("getCategory");
            return blogcontrolService.getCategory();
        };
    }

    /**
     *  블로그 글 생성.
     *
     * @param request
     * @param blogInfo
     * @param fileList
     * @return
     * @throws Exception
     */
    @PostMapping(value = "create", produces = "application/json;charset=utf-8")
    public Callable<Object> createBolog(final HttpServletRequest request, @RequestParam("blogInfo") String blogInfo, @RequestPart(required = false) MultipartFile[] fileList) throws Exception {

        return () -> {
            log.error("createBolog");
            BlogCreateRequest blogCreateRequest = new ObjectMapper().readValue(blogInfo, BlogCreateRequest.class);
            return blogcontrolService.createBolog(blogCreateRequest, fileList);
        };
    }

    /**
     *  블로그 글 목록 불러오기
     *
     * @param request

     * @return
     * @throws Exception
     */

    @GetMapping(value = "/list" , produces = "application/json;charset=utf-8")
    public Callable<Object> getBlogList(final HttpServletRequest request) throws Exception {
        return () -> {
            return blogcontrolService.getBlogList(request);
        };
    }
    @GetMapping(value = "/{blogid}" , produces = "application/json;charset=utf-8")
    public Callable<Object> getBlogDetail(final HttpServletRequest request, @PathVariable long blogid) throws Exception {
        return () -> {
            return blogcontrolService.getBlogDetail(blogid);
        };
    }

    @PostMapping(value ="edit", produces = "application/json;charset=utf-8")
    public Callable<Object> blogDetailUpdate(final HttpServletRequest request, @RequestParam("blogEditInfo") String blogEditInfo, @RequestPart(required = false) MultipartFile[] fileList) throws Exception {
        return () -> {
            log.error("createBolog");
            BlogEditRequest blogEditRequest = new ObjectMapper().readValue(blogEditInfo, BlogEditRequest.class);
            return blogcontrolService.blogDetailUpdate(blogEditRequest, fileList);
        };
    }
}
