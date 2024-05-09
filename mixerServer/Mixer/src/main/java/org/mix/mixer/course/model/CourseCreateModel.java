package org.mix.mixer.course.model;

public record CourseCreateModel(
        String title,
        Integer memberCount,
        Integer price,
        Integer courseTime,
        String description
) {
}
