package com.jwt.security.service;

import com.jwt.security.Entity.course.Course;
import com.jwt.security.Entity.course.Modules;
import com.jwt.security.repository.CourseRepository;
import com.jwt.security.repository.ModulesRepository;
import com.jwt.security.Entity.user.User;
import com.jwt.security.exception.CustomException;
import com.jwt.security.requestResponse.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModulesService {
    private final CourseRepository courseRepository;
    private final ModulesRepository modulesRepository;

    public List<ModulesResponse> addModule(AddModuleRequest request) {
        long courseId = request.getCourseId();
        List<ModulesRequest> moduleRequests = request.getModules();
        List<ModulesResponse> listModulesResponses = new ArrayList<>();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException("Course not found"));
        for (ModulesRequest moduleRequest : moduleRequests) {
            Modules modules = new Modules();
            modules.setModuleNumber(moduleRequest.getModuleNumber());
            modules.setTitle(moduleRequest.getTitle());
            modules.setDescription(moduleRequest.getDescription());
            modules.setCode(moduleRequest.getCode());
            modules.setCourse(course);

            course.getModules().add(modules);
        }
        // Сохранение всех модулей в базе данных
        List<Modules> savedModules = modulesRepository.saveAll(course.getModules());

        for (Modules savedModule : savedModules) {
            listModulesResponses.add(modulesResponse(savedModule, savedModule.getId()));
        }
        courseRepository.save(course);
        return listModulesResponses;
    }

    public List<ModulesResponse> getModules(Long id, User user) {
        Long courseId = courseRepository.findById(id)
                .orElseThrow(() -> new CustomException("Course not found")).getId();
        Long creatorId = user.getCourseCreator().getId();
        List<Modules> ListModules =
                modulesRepository.findModulesByCourseIdAndCreatorId(courseId, creatorId);

        List<ModulesResponse> listModulesResponses = new ArrayList<>();
        for (Modules modules : ListModules) {
            listModulesResponses.add(modulesResponse(modules, null));
        }
        return listModulesResponses;
    }

    public ModulesResponse modulesResponse(Modules modules, Long id) {
        ModulesResponse modulesResponse = new ModulesResponse();
        if (id == null) {
            modulesResponse.setId(modules.getId());
        } else {
            modulesResponse.setId(id);
        }
        modulesResponse.setModuleNumber(modules.getModuleNumber());
        modulesResponse.setTitle(modules.getTitle());
        modulesResponse.setDescription(modules.getDescription());
        modulesResponse.setCode(modulesResponse.getCode());
        return modulesResponse;
    }

    public List<ModulesResponse> updateModules(UpdateDeleteRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new CustomException("Course not found"));

        List<ModulesRequest> moduleRequests = request.getModules();
        List<ModulesResponse> listModulesResponses = new ArrayList<>();

        // Получение списка идентификаторов модулей, присутствующих в текущем запросе
        List<Long> moduleRequestIds = moduleRequests.stream()
                .map(ModulesRequest::getId)
                .toList();

        // Получение списка модулей, которые нужно удалить
        List<Modules> modulesToDelete = course.getModules().stream()
                .filter(module -> !moduleRequestIds.contains(module.getId()))
                .toList();

        // Удаление модулей из списка модулей
        course.getModules().removeAll(modulesToDelete);
        modulesRepository.deleteAll(modulesToDelete);

        // Обновление каждого модуля
        for (ModulesRequest moduleRequest : moduleRequests) {
            Long moduleRequestId = moduleRequest.getId();
            Modules modules = getModules(moduleRequest, moduleRequestId, course);

            if (!course.getModules().contains(modules)) {
                course.getModules().add(modules);
            }
        }
        // Сохранение всех модулей в базе данных
        List<Modules> savedModules = modulesRepository.saveAll(course.getModules());
        // Создание объектов ModulesResponse и добавление их в список
        for (Modules savedModule : savedModules) {
            listModulesResponses.add(modulesResponse(savedModule, savedModule.getId()));
        }

        courseRepository.save(course);
        return listModulesResponses;
    }

    public Message deleteModules(UpdateDeleteRequest request) {
        long courseId = request.getCourseId();
        List<ModulesRequest> moduleRequests = request.getModules();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException("Course not found"));
        // Получение списка идентификаторов модулей, присутствующих в текущем запросе
        List<Long> moduleRequestIds = moduleRequests.stream()
                .map(ModulesRequest::getId)
                .toList();
        // Получение списка модулей, которые нужно удалить
        List<Modules> modulesToDelete = course.getModules().stream()
                .filter(module -> moduleRequestIds.contains(module.getId()))
                .toList();
        // Удаление модулей из списка модулей курса
        course.getModules().removeAll(modulesToDelete);

        // Удаление модулей, которые отсутствуют в текущем запросе, из базы данных
        modulesRepository.deleteAll(modulesToDelete);
        return new Message("delete");
    }

    public List<Modules> updateModules(Course course, List<ModulesRequest> moduleRequests) {
        // Формирование списка модулей для удаления из курса
        List<Modules> modulesToDelete = course.getModules().stream()
                .filter(module -> moduleRequests.stream().noneMatch(mr -> Objects.equals(mr.getId(), module.getId())))
                .toList();

        course.getModules().removeAll(modulesToDelete);
        modulesRepository.deleteAll(modulesToDelete);

        // Обновление модулей курса на основе данных из запроса
        for (ModulesRequest moduleRequest : moduleRequests) {
            Long moduleRequestId = moduleRequest.getId();

            // Получение существующего модуля, если он существует, или создание нового
            Modules modules = getModules(moduleRequest, moduleRequestId, course);

            // Установка пустого списка уроков, если список не определен
            if (modules.getLessons() == null) {
                modules.setLessons(new ArrayList<>());
            }
            // Добавление модуля в список модулей курса, если его там нет
            if (!course.getModules().contains(modules)) {
                course.getModules().add(modules);
            }
        }
        return course.getModules();
    }


    public ModulesResponse getModulesResponse(Modules modules) {
        ModulesResponse modulesResponse = new ModulesResponse();
        modulesResponse.setId(modules.getId());
        modulesResponse.setTitle(modules.getTitle());
        modulesResponse.setDescription(modules.getDescription());
        modulesResponse.setModuleNumber(modules.getModuleNumber());
        modulesResponse.setCode(modules.getCode());
        return modulesResponse;
    }
    public Modules getModules(ModulesRequest moduleRequest,Long moduleRequestId,  Course course){
        Modules modules = course.getModules().stream()
                .filter(module -> Objects.equals(module.getId(), moduleRequestId) && module.getId() != null)
                .findFirst()
                .orElseGet(Modules::new);
        modules.setTitle(moduleRequest.getTitle());
        modules.setDescription(moduleRequest.getDescription());
        modules.setModuleNumber(moduleRequest.getModuleNumber());
        modules.setCourse(course);
        modules.setCode(moduleRequest.getCode());
        return modules;
    }
}
