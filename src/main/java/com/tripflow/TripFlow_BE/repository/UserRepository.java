package com.tripflow.TripFlow_BE.repository;

import com.tripflow.TripFlow_BE.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByNickname(String nickname);
    Optional<UserInfo> findByUserid(String userid);
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByPhonenumber(String phonenumber);
    Optional<UserInfo> findByPassword(String password);
    List<UserInfo> findAll();
}
