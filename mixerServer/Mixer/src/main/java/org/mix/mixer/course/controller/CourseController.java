package org.mix.mixer.course.controller;

import org.mix.mixer.course.model.CourseCreateModel;
import org.mix.mixer.course.model.CourseResponseModel;
import org.mix.mixer.course.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/crud/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createCourse(@RequestBody CourseCreateModel model) {
        CourseResponseModel createdCourse = courseService.save(model);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteCourse(@RequestBody CourseCreateModel model) {
        courseService.delete(model);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/createAll")
    public ResponseEntity<?> createCourses(@RequestBody List<CourseCreateModel> models) {
        List<CourseResponseModel> createdCourses = courseService.saveAll(models);
        return new ResponseEntity<>(createdCourses, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteCourses(@RequestBody List<CourseCreateModel> models) {
        courseService.deleteAll(models);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable("courseId") Long courseId, @RequestBody CourseCreateModel model) {
        courseService.update(courseId, model);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
