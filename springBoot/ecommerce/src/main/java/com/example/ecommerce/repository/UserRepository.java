package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
