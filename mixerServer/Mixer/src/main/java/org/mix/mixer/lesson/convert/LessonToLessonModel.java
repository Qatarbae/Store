package org.mix.mixer.lesson.convert;

import org.mix.mixer.lesson.entity.Lesson;
import org.mix.mixer.lesson.model.LessonModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonToLessonModel implements LessonConvert<LessonModel, Lesson> {

    @Override
    public LessonModel toConvert(Lesson model) {
        return new LessonModel(
                model.getId(),
                model.getTitle(),
                model.getCode(),
                model.getLessonNumber(),
                model.getModules().getId()
        );
    }

    @Override
    public List<LessonModel> toListConvert(List<Lesson> models) {
        List<LessonModel> lessonModels = new ArrayList<>();
        for (Lesson lesson : models) {
            lessonModels.add(toConvert(lesson));
        }
        return lessonModels;
    }
}
