package org.mix.mixer.course.repository;

import org.mix.mixer.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByTitle(String title);
    List<Course> findByCourseCreatorId(Long creatorId);
    List<Course> findByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT c FROM Course c" +
            " WHERE levenshtein(:title, c.title) <= :maxDistance")
    List<Course> findByTitleLevenshteinDistance(@Param("title") String title,
                                                @Param("maxDistance") int maxDistance);

    @Query(value = "SELECT * FROM course" +
            " TABLESAMPLE SYSTEM(1) LIMIT :limit", nativeQuery = true)
    List<Course> findRandomCourses(@Param("limit") int limit);
    @Query(value = "SELECT COUNT(*) FROM course", nativeQuery = true)
    int getTotalCourseCount();
}
