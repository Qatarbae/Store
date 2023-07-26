package com.jwt.security.repository;

import com.jwt.security.Entity.user.UserStudies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStudiesRepository extends JpaRepository<UserStudies, Long> {

}
