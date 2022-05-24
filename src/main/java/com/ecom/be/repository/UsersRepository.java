package com.ecom.be.repository;

import com.ecom.be.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    //find Users by username
    Users findByUserName(String userName);
}
