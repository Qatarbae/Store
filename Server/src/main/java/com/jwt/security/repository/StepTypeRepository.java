package com.jwt.security.repository;

import com.jwt.security.Entity.course.Categories;
import com.jwt.security.Entity.course.StepType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepTypeRepository extends JpaRepository<StepType, Long> {
}
