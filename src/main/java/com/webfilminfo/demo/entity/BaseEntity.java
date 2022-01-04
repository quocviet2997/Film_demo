package com.webfilminfo.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @CreatedDate
    private Timestamp createDate;
    @Column
    @CreatedBy
    private String createBy;
    @Column
    @LastModifiedDate
    private Timestamp updateDate;
    @Column
    @LastModifiedBy
    private String updateBy;
    @Column(name = "status")
    private Integer status;
}
