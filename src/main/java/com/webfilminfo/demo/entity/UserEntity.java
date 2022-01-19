package com.webfilminfo.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
@Data
public class UserEntity extends BaseEntity {
    @Column(name = "userName", nullable = false, unique = true)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "userId")
    @JsonIgnore
    private List<CommentEntity> comments = new ArrayList<>();
}
