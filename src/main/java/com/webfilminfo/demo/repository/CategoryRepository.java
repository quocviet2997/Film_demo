package com.webfilminfo.demo.repository;

import com.webfilminfo.demo.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <CategoryEntity, Long>{
}
