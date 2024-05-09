package org.mix.mixer.module.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.mix.mixer.course.entity.Course;
import org.mix.mixer.course.repository.CourseRepository;
import org.mix.mixer.module.convert.ModulesModelToModules;
import org.mix.mixer.module.convert.ModulesToModulesResponse;
import org.mix.mixer.module.entity.Modules;
import org.mix.mixer.module.model.ModulesModel;
import org.mix.mixer.module.repository.ModulesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModulesService {

    private final ModulesRepository modulesRepository;
    private final CourseRepository courseRepository;
    private final ModulesModelToModules toModules;
    private final ModulesToModulesResponse toModulesResponse;
    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    public ModulesModel save(ModulesModel model) {
        Course course = courseRepository.findById(model.courseId()).orElseThrow();
        Modules modules = toModules.toConvert(model);
        modules.setCourse(course);
        modules = modulesRepository.save(modules);

        course.getModules().add(modules);
        courseRepository.save(course);

        return toModulesResponse.toConvert(modules);
    }

    @Transactional
    public void delete(Long modulesId, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        Modules modules = modulesRepository.findById(modulesId).orElseThrow();
        course.getModules().remove(modules);
        modulesRepository.delete(modules);
    }

    @Transactional
    public List<ModulesModel> saveAll(List<ModulesModel> models, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        List<Modules> modulesList = modulesRepository.saveAll(toModules.toListConvert(models));
        for (Modules module : modulesList) {
            module.setCourse(course); // Присвоение курса каждому модулю
        }
        modulesList = modulesRepository.saveAll(modulesList);
        return toModulesResponse.toListConvert(modulesList);
    }

    @Transactional
    public void deleteAll(Long courseId) {
        List<Modules> modulesList = modulesRepository.findByCourseId(courseId);
        modulesRepository.deleteAll(modulesList);
    }

    @Transactional
    public Modules update(Long modulesId, ModulesModel model) {
        Modules modules = modulesRepository.findById(modulesId).orElseThrow();
        modules = modulesRepository.save(toModules.toConvert(modules, model));
        return modules;
    }
}
