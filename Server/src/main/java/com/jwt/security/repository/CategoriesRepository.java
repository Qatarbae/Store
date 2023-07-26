package com.jwt.security.repository;

import com.jwt.security.Entity.course.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    boolean existsByName(String name);
}
