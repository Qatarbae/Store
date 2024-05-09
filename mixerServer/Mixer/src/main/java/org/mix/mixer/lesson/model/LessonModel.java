package org.mix.mixer.lesson.model;

public record LessonModel(
        Long id,
        String title,
        Integer code,
        Double lessonNumber,
        Long modulesId
) {
}
