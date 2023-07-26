package com.jwt.security.repository;

import com.jwt.security.Entity.user.CourseCreator;
import com.jwt.security.Entity.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseCreatorRepository extends JpaRepository<CourseCreator, Long> {
}
