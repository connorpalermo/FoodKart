package com.connortest.repository;

import com.connortest.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findByPhone(String phone);

}
