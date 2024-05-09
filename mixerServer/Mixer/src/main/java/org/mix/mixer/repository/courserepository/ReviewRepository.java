package org.mix.mixer.repository.courserepository;

import org.mix.mixer.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
