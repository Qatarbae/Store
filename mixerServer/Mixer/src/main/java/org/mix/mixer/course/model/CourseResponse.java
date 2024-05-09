package org.mix.mixer.course.model;

public record CourseResponse(
        Long id,
        String title,
        Integer memberCount,
        Integer price,
        Integer courseTime,
        String description
) {
}
