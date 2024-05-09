package org.mix.mixer.lesson.convert;

import org.mix.mixer.lesson.entity.Lesson;
import org.mix.mixer.lesson.model.LessonModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonModelToLesson implements LessonConvert<Lesson, LessonModel> {

    @Override
    public Lesson toConvert(LessonModel model) {
        return Lesson.builder()
                .id(model.id())
                .title(model.title())
                .code(model.code())
                .lessonNumber(model.lessonNumber())
                .build();
    }

    @Override
    public List<Lesson> toListConvert(List<LessonModel> models) {
        List<Lesson> lessons = new ArrayList<>();
        for (LessonModel model : models) {
            lessons.add(toConvert(model));
        }
        return lessons;
    }
}
