package com.webfilminfo.demo.dto;

import com.webfilminfo.demo.entity.FilmEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseObject {
    private String status;
    private String message;
    private Integer totalPage;
    private Integer limit;
    private Integer page;
    private Object data;

    public ResponseObject(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
