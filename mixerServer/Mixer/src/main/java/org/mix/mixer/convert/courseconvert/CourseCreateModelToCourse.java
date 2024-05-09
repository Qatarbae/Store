package org.mix.mixer.convert.courseconvert;

import org.mix.mixer.course.entity.Course;
import org.mix.mixer.model.course.coursemodel.CourseCreateModel;

public class CourseCreateModelToCourse implements CourseConvert<Course, CourseCreateModel>{

    @Override
    public Course toConvert(CourseCreateModel model) {
        return Course.builder()
                .title(model.getTitle())
                .memberCount(model.getMemberCount())
                .price(model.getPrice())
                .courseTime(model.getCourseTime())
                .description(model.getDescription())
                .build();
    }
}
