package org.mix.mixer.course.convert;

import org.mix.mixer.course.entity.Course;
import org.mix.mixer.course.model.CourseCreateModel;
import org.mix.mixer.course.model.CourseResponseModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseCreateModelToCourse implements CourseConvert<Course, CourseCreateModel>{

    @Override
    public Course toConvert(CourseCreateModel model) {
        return Course.builder()
                .title(model.title())
                .memberCount(model.memberCount())
                .price(model.price())
                .courseTime(model.courseTime())
                .description(model.description())
                .build();
    }

    @Override
    public List<Course> toListConvert(List<CourseCreateModel> models) {
        List<Course> courses = new ArrayList<>();
        for (CourseCreateModel model : models) {
            courses.add(toConvert(model));
        }
        return courses;
    }

    public Course toConvert(Course course, CourseCreateModel model){
        if (course != null) {
            course.setTitle(model.title());
            course.setMemberCount(model.memberCount());
            course.setPrice(model.price());
            course.setCourseTime(model.courseTime());
            course.setDescription(model.description());
        }
        return course;
    }
}
