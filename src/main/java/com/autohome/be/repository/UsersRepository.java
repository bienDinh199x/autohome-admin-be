package com.autohome.be.repository;

import com.autohome.be.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    //find Users by username
    Users findByUserName(String userName);
}
