package com.jwt.security.repository;

import com.jwt.security.Entity.text.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
