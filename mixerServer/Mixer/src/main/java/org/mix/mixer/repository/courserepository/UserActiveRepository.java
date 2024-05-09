package org.mix.mixer.repository.courserepository;


import org.mix.mixer.entity.appuser.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActiveRepository extends JpaRepository<UserActivity, Long> {
}