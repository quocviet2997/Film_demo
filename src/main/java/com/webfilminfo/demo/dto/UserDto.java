package com.webfilminfo.demo.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto extends BaseDto {
    private String userName;
    private String password;
    private String role;
    private List<CommentDto> comments;
}
