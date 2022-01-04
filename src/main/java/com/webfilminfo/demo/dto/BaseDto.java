package com.webfilminfo.demo.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
public class BaseDto {
    private Long id;
    private Timestamp createDate;
    private String createBy;
    private Timestamp updateDate;
    private String updateBy;
}
