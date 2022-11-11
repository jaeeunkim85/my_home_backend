package com.project.MyHome.controller;

import com.project.MyHome.service.FileSaverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

@RestController
@Slf4j
@RequestMapping("/v1/file")
public class FileSaverController {

    @Autowired
    FileSaverService fileSaverService;

    /**
     * 게시글 이미지 파일 업로드
     *
     * @param request
     * @param fileList
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public Callable<Object> imageUpload(final HttpServletRequest request, @RequestParam("files") MultipartFile[] fileList) throws Exception {
        return () -> {
            log.error("imageUpload");
            return fileSaverService.uploadFiles(fileList);
        };
    }

    @GetMapping(value = "/test")
    public Callable<Object> getTest(final HttpServletRequest request) {
        return () -> {
            log.error("getTest");
            return true;
        };
    }
}
