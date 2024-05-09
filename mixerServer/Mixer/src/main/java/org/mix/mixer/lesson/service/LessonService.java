package org.mix.mixer.lesson.service;

import lombok.RequiredArgsConstructor;
import org.mix.mixer.lesson.convert.LessonModelToLesson;
import org.mix.mixer.lesson.convert.LessonToLessonModel;
import org.mix.mixer.lesson.entity.Lesson;
import org.mix.mixer.lesson.model.LessonModel;
import org.mix.mixer.lesson.repository.LessonRepository;
import org.mix.mixer.module.entity.Modules;
import org.mix.mixer.module.repository.ModulesRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ModulesRepository modulesRepository;
    private final LessonModelToLesson toLesson;
    private final LessonToLessonModel toLessonModel;

    public LessonModel save(LessonModel model) {
        Modules module = modulesRepository.findById(model.modulesId())
                .orElseThrow();
        Lesson lesson = toLesson.toConvert(model);
        lesson.setModules(module);
        lesson = lessonRepository.save(lesson);

        return toLessonModel.toConvert(lesson);
    }

    public void delete(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }

    public List<LessonModel> saveAll(List<LessonModel> models, Long modulesId) {
        Modules modules = modulesRepository.findById(modulesId).orElseThrow();
        List<Lesson> lessons = toLesson.toListConvert(models);
        for (Lesson model : lessons) {
            model.setModules(modules);
        }
        lessons = lessonRepository.saveAll(lessons);
        return toLessonModel.toListConvert(lessons);
    }

    public void deleteAll(Long moduleId) {
        lessonRepository.deleteAllByModulesId(moduleId);
    }
}
