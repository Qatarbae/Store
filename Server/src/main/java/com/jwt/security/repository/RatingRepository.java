package com.jwt.security.repository;

import com.jwt.security.Entity.text.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository  extends JpaRepository<Rating, Long> {
}
