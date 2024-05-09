package org.mix.mixer.course.convert;

import org.mix.mixer.course.entity.Course;
import org.mix.mixer.course.model.CourseResponseModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseToCourseResponse implements CourseConvert<CourseResponseModel, Course>{

    @Override
    public CourseResponseModel toConvert(Course model) {
        return new CourseResponseModel(
                model.getId(),
                model.getTitle(),
                model.getMemberCount(),
                model.getPrice(),
                model.getCourseTime(),
                model.getDescription()
        );
    }

    @Override
    public List<CourseResponseModel> toListConvert(List<Course> models) {
        List<CourseResponseModel> responseModels = new ArrayList<>();
        for (Course model : models) {
            responseModels.add(toConvert(model));
        }
        return responseModels;
    }
}
