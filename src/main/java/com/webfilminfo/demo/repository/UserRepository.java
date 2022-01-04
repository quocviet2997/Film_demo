package com.webfilminfo.demo.repository;

import com.webfilminfo.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUserNameAndPassword(String userName, String password);
}
