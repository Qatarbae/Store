package org.mix.mixer.lesson.controller;

import org.mix.mixer.lesson.model.LessonModel;
import org.mix.mixer.lesson.service.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/crud/lesson")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/save")
    public ResponseEntity<LessonModel> saveLesson(@RequestBody LessonModel model) {
        LessonModel savedLesson = lessonService.save(model);
        return ResponseEntity.ok(savedLesson);
    }

    @DeleteMapping("/delete/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long lessonId) {
        lessonService.delete(lessonId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saveAll/{modulesId}")
    public ResponseEntity<List<LessonModel>> saveAllLessons(@RequestBody List<LessonModel> models, @PathVariable Long modulesId) {
        List<LessonModel> savedLessons = lessonService.saveAll(models, modulesId);
        return ResponseEntity.ok(savedLessons);
    }

    @DeleteMapping("/deleteAll/{moduleId}")
    public ResponseEntity<?> deleteAllLessons(@PathVariable Long moduleId) {
        lessonService.deleteAll(moduleId);
        return ResponseEntity.ok().build();
    }
}

