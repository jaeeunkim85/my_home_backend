package com.project.MyHome.response;

import com.project.MyHome.utils.Constants;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Response {
    private String status;
    private int statusCode;
    private Object data;

    private Response() {
        status = Constants.STATUS_OK;
        statusCode = HttpStatus.OK.value();
        data = new HashMap<String, Object>();
    }

    private Response(Map inputData) {
        status = Constants.STATUS_OK;
        statusCode = HttpStatus.OK.value();
        data = new HashMap<String, Object>();
        ((HashMap<String, Object>) data).putAll(inputData);
    }

    private Response(List inputData) {
        status = Constants.STATUS_OK;
        statusCode = HttpStatus.OK.value();
        data = new ArrayList();
        ((ArrayList) data).addAll(inputData);
    }

    private Response(Object inputData) {
        status = Constants.STATUS_OK;
        statusCode = HttpStatus.OK.value();
        data = inputData;
    }

    public void setData(Object value) {
        if (data instanceof List) {
            ((List<Object>)data).add(value);
        } else {
            data = value;
        }
    }

    public static Response from() {
        return new Response();
    }
}
