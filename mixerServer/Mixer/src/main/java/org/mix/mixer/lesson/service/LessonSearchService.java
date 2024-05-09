package org.mix.mixer.lesson.service;

import lombok.RequiredArgsConstructor;
import org.mix.mixer.lesson.convert.LessonToLessonModel;
import org.mix.mixer.lesson.entity.Lesson;
import org.mix.mixer.lesson.model.LessonModel;
import org.mix.mixer.lesson.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonSearchService {

    private final LessonRepository lessonRepository;
    private final LessonToLessonModel toLessonModel;

    public LessonModel findById(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        return toLessonModel.toConvert(lesson);
    }

    public List<LessonModel> findAllByModulesId(Long modulesId) {
        List<Lesson> lessons = lessonRepository.findLessonsByModulesId(modulesId);
        return toLessonModel.toListConvert(lessons);
    }
}
