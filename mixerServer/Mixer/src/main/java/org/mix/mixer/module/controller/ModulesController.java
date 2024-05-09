package org.mix.mixer.module.controller;

import org.mix.mixer.module.entity.Modules;
import org.mix.mixer.module.model.ModulesModel;
import org.mix.mixer.module.service.ModulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/crud/modules")
public class ModulesController {

    private final ModulesService modulesService;

    public ModulesController(ModulesService modulesService) {
        this.modulesService = modulesService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveModule(@RequestBody ModulesModel model) {
        ModulesModel savedModule = modulesService.save(model);
        return ResponseEntity.ok(savedModule);
    }

    @DeleteMapping("/delete/{moduleId}/{courseId}")
    public ResponseEntity<?> deleteModule(@PathVariable Long moduleId, @PathVariable Long courseId) {
        modulesService.delete(moduleId, courseId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saveAll/{courseId}")
    public ResponseEntity<?> saveAllModules(@RequestBody List<ModulesModel> models, @PathVariable Long courseId) {
        List<ModulesModel> savedModules = modulesService.saveAll(models, courseId);
        return ResponseEntity.ok(savedModules);
    }

    @DeleteMapping("/deleteAll/{courseId}")
    public ResponseEntity<?> deleteAllModules(@PathVariable Long courseId) {
        modulesService.deleteAll(courseId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{moduleId}")
    public ResponseEntity<?> updateModule(@PathVariable Long moduleId, @RequestBody ModulesModel model) {
        Modules updatedModule = modulesService.update(moduleId, model);
        return ResponseEntity.ok(updatedModule);
    }
}
