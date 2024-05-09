package org.mix.mixer.repository.courserepository;

import org.mix.mixer.entity.course.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
}
