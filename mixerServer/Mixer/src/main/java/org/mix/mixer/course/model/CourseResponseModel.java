package org.mix.mixer.course.model;

public record CourseResponseModel(
        Long id,
        String title,
        Integer memberCount,
        Integer price,
        Integer courseTime,
        String description
) {
}
