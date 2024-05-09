package org.mix.mixer.convert.courseconvert;

import org.mix.mixer.course.entity.Course;
import org.mix.mixer.course.model.CourseResponse;

public class CourseToCourseResponse implements CourseConvert<CourseResponse, Course>{

    @Override
    public CourseResponse toConvert(Course model) {
        return null;
    }
}
