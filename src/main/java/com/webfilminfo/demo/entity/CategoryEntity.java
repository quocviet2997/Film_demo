package com.webfilminfo.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "category")
@Data
public class CategoryEntity extends BaseEntity{
    @Column(name="categoryName", nullable = false, unique = true)
    private String categoryName;
    @OneToMany(mappedBy = "categoryId")
    @JsonIgnore
    private List<CategoryFilmEntity> filmCategories;
}
