package org.mix.mixer.course.controller;

import org.mix.mixer.course.model.CourseResponseModel;
import org.mix.mixer.course.service.CourseSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/search/course")
public class CourseSearchController {

    private final CourseSearchService courseSearchService;

    public CourseSearchController(CourseSearchService courseSearchService) {
        this.courseSearchService = courseSearchService;
    }

    @GetMapping("title/{title}")
    public ResponseEntity<?> findCourseByTitle(@PathVariable("title") String title) {
        CourseResponseModel course = courseSearchService.findCourseByTitle(title);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("creator/{creatorId}")
    public ResponseEntity<?> findCoursesByCreatorId(
            @PathVariable("creatorId") Long creatorId
    ) {
        List<CourseResponseModel> courses = courseSearchService.findCoursesByCreatorId(creatorId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("titleContaining/{title}")
    public ResponseEntity<?> findCoursesByTitleContainingIgnoreCase(
            @PathVariable("title") String title
    ) {
        List<CourseResponseModel> courses = courseSearchService.findCoursesByTitleContainingIgnoreCase(title);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("titleLevenshtein")
    public ResponseEntity<?> findCoursesByTitleLevenshteinDistance(
            @RequestParam("title") String title,
            @RequestParam("maxDistance") int maxDistance
    ) {
        List<CourseResponseModel> courses = courseSearchService.findCoursesByTitleLevenshteinDistance(title, maxDistance);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("random")
    public ResponseEntity<?> findRandomCourses(
            @RequestParam("limit") int limit
    ) {
        List<CourseResponseModel> courses = courseSearchService.findRandomCourses(limit);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("count")
    public ResponseEntity<?> getTotalCourseCount() {
        int totalCount = courseSearchService.getTotalCourseCount();
        return new ResponseEntity<>(totalCount, HttpStatus.OK);
    }
}
