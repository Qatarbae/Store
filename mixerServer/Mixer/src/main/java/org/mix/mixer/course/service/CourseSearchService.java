package org.mix.mixer.course.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mix.mixer.course.convert.CourseCreateModelToCourse;
import org.mix.mixer.course.convert.CourseToCourseResponse;
import org.mix.mixer.course.entity.Course;
import org.mix.mixer.course.model.CourseCreateModel;
import org.mix.mixer.course.model.CourseResponseModel;
import org.mix.mixer.course.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseSearchService {
    private final CourseRepository courseRepository;
    private final CourseToCourseResponse toCourseResponse;
    @PersistenceContext
    private final EntityManager entityManager;


    // Метод для поиска курса по названию
    public CourseResponseModel findCourseByTitle(String title) {
        Optional<Course> optionalCourse = courseRepository.findByTitle(title);
        return optionalCourse.map(toCourseResponse::toConvert).orElse(null);
    }

    // Метод для поиска курсов, созданных определенным создателем
    public List<CourseResponseModel> findCoursesByCreatorId(Long creatorId) {
        List<Course> courses = courseRepository.findByCourseCreatorId(creatorId);
        return toCourseResponse.toListConvert(courses);
    }

    // Метод для поиска курсов, названия которых содержат указанную строку
    public List<CourseResponseModel> findCoursesByTitleContainingIgnoreCase(String title) {
        List<Course> courses = courseRepository.findByTitleContainingIgnoreCase(title);
        return toCourseResponse.toListConvert(courses);
    }

    // Метод для поиска курсов с использованием расстояния Левенштейна
    public List<CourseResponseModel> findCoursesByTitleLevenshteinDistance(String title, int maxDistance) {
        List<Course> courses = courseRepository.findByTitleLevenshteinDistance(title, maxDistance);
        return toCourseResponse.toListConvert(courses);
    }

    // Метод для поиска случайных курсов
    public List<CourseResponseModel> findRandomCourses(int limit) {
        List<Course> courses = courseRepository.findRandomCourses(limit);
        return toCourseResponse.toListConvert(courses);
    }

    // Метод для получения общего количества курсов в базе данных
    public int getTotalCourseCount() {
        return courseRepository.getTotalCourseCount();
    }
}
