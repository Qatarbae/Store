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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseCreateModelToCourse toCourse;
    private final CourseToCourseResponse toCourseResponse;
    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    public CourseResponseModel save(CourseCreateModel model){
        Course course = courseRepository.save(toCourse.toConvert(model));
        return toCourseResponse.toConvert(course);
    }
    @Transactional
    public void delete(CourseCreateModel model){
        courseRepository.delete(toCourse.toConvert(model));
    }
    @Transactional
    public List<CourseResponseModel> saveAll(List<CourseCreateModel> models){
        List<Course> courses = courseRepository.saveAll(toCourse.toListConvert(models));
        return toCourseResponse.toListConvert(courses);
    }
    @Transactional
    public  void deleteAll(List<CourseCreateModel> models){
        courseRepository.deleteAll(toCourse.toListConvert(models));
    }
    @Transactional
    public  void update(Long courseId, CourseCreateModel model){
        Course course = entityManager.find(Course.class, courseId);
        if (course != null) {
            entityManager.merge(toCourse.toConvert(course,model));
        }
    }
}
