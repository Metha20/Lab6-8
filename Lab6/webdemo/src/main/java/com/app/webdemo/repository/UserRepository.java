package com.app.webdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.webdemo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
