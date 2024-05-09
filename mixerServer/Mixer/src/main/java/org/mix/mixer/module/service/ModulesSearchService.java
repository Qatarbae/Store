package org.mix.mixer.module.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.mix.mixer.course.repository.CourseRepository;
import org.mix.mixer.module.convert.ModulesModelToModules;
import org.mix.mixer.module.convert.ModulesToModulesResponse;
import org.mix.mixer.module.entity.Modules;
import org.mix.mixer.module.model.ModulesModel;
import org.mix.mixer.module.repository.ModulesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ModulesSearchService {

    private final ModulesRepository modulesRepository;
    private final ModulesToModulesResponse toModulesResponse;

    public ModulesModel findById(Long modulesId) {
        Modules modules = modulesRepository.findById(modulesId).orElseThrow();
        return toModulesResponse.toConvert(modules);
    }

    public List<ModulesModel> findByCourseId(Long courseId) {
        List<Modules> modulesList = modulesRepository.findByCourseId(courseId);
        return toModulesResponse.toListConvert(modulesList);
    }

}
