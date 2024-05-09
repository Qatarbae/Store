package org.mix.mixer.module.controller;

import org.mix.mixer.module.model.ModulesModel;
import org.mix.mixer.module.service.ModulesSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/search/modules")
public class ModulesSearchController {

    private final ModulesSearchService modulesSearchService;

    public ModulesSearchController(ModulesSearchService modulesSearchService) {
        this.modulesSearchService = modulesSearchService;
    }

    @GetMapping("/{moduleId}")
    public ResponseEntity<?> findModuleById(@PathVariable Long moduleId) {
        ModulesModel module = modulesSearchService.findById(moduleId);
        return ResponseEntity.ok(module);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> findModulesByCourseId(@PathVariable Long courseId) {
        List<ModulesModel> modules = modulesSearchService.findByCourseId(courseId);
        return ResponseEntity.ok(modules);
    }
}
