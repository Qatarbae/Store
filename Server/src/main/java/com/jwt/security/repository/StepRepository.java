package com.jwt.security.repository;

import com.jwt.security.Entity.course.Categories;
import com.jwt.security.Entity.course.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
}
