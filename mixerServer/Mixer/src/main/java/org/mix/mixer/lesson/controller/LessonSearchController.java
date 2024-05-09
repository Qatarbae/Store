package org.mix.mixer.lesson.controller;

import org.mix.mixer.lesson.model.LessonModel;
import org.mix.mixer.lesson.service.LessonSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/search/lesson")
public class LessonSearchController {

    private final LessonSearchService lessonSearchService;

    public LessonSearchController(LessonSearchService lessonSearchService) {
        this.lessonSearchService = lessonSearchService;
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<?> findLessonById(@PathVariable Long lessonId) {
        LessonModel lesson = lessonSearchService.findById(lessonId);
        return ResponseEntity.ok(lesson);
    }

    @GetMapping("/modules/{modulesId}")
    public ResponseEntity<?> findLessonsByModulesId(@PathVariable Long modulesId) {
        List<LessonModel> lessons = lessonSearchService.findAllByModulesId(modulesId);
        return ResponseEntity.ok(lessons);
    }
}