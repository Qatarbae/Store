package com.jwt.security.service;

import com.jwt.security.Entity.course.Course;
import com.jwt.security.Entity.course.Modules;
import com.jwt.security.Entity.course.repository.CourseRepository;
import com.jwt.security.Entity.course.repository.ModulesRepository;
import com.jwt.security.Entity.user.User;
import com.jwt.security.exception.YourCustomException;
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
        List<AddModulesRequest> moduleRequests = request.getModules();
        List<ModulesResponse> listModulesResponses = new ArrayList<>();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new YourCustomException("Course not found"));
        for (AddModulesRequest moduleRequest : moduleRequests) {
            Modules modules = new Modules();
            modules.setModuleNumber(moduleRequest.getModulesNumber());
            modules.setName(moduleRequest.getName());
            modules.setDescription(moduleRequest.getDescription());
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
                .orElseThrow(() -> new YourCustomException("Course not found")).getId();
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
        modulesResponse.setModulesNumber(modules.getModuleNumber());
        modulesResponse.setName(modules.getName());
        modulesResponse.setDescription(modules.getDescription());

        return modulesResponse;
    }

    public List<ModulesResponse> updateModules(UpdateDeleteRequest request) {
        long courseId = request.getCourseId();
        List<ModulesRequest> moduleRequests = request.getModules();

        // Получение курса по courseId или выброс исключения, если курс не найден
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new YourCustomException("Course not found"));

        // Список для хранения ответов с информацией об обновленных модулях
        List<ModulesResponse> listModulesResponses = new ArrayList<>();

        // Получение списка идентификаторов модулей, присутствующих в текущем запросе
        List<Long> moduleRequestIds = moduleRequests.stream()
                .map(ModulesRequest::getId)
                .collect(Collectors.toList());

        // Получение списка модулей, которые нужно удалить
        List<Modules> modulesToDelete = course.getModules().stream()
                .filter(module -> !moduleRequestIds.contains(module.getId()))
                .collect(Collectors.toList());

        // Удаление модулей из списка модулей курса
        course.getModules().removeAll(modulesToDelete);

        // Удаление модулей, которые отсутствуют в текущем запросе, из базы данных
        modulesRepository.deleteAll(modulesToDelete);

        // Обновление каждого модуля
        for (ModulesRequest moduleRequest : moduleRequests) {
            Long moduleRequestId = moduleRequest.getId();

            // Поиск модуля для обновления по moduleRequestId
            Modules modules = course.getModules().stream()
                    .filter(module -> module.getId().equals(moduleRequestId))
                    .findFirst()
                    .orElseGet(Modules::new);

            // Применение изменений из ModuleRequest
            modules.setCourse(course);
            modules.setName(moduleRequest.getName());
            modules.setDescription(moduleRequest.getDescription());
            modules.setModuleNumber(moduleRequest.getModulesNumber());

            // Добавление модуля в список модулей курса и список для обновления
            if (!course.getModules().contains(modules)) {
                course.getModules().add(modules);
                //modulesToUpdate.add(modules);
            }
        }

        // Сохранение всех модулей в базе данных
        List<Modules> savedModules = modulesRepository.saveAll(course.getModules());

        // Создание объектов ModulesResponse и добавление их в список
        for (Modules savedModule : savedModules) {
            listModulesResponses.add(modulesResponse(savedModule, savedModule.getId()));
        }

        // Сохранение обновленного курса
        courseRepository.save(course);

        // Возвращение списка с информацией об обновленных модулях
        return listModulesResponses;
    }

    public Message deleteModules(UpdateDeleteRequest request) {
        long courseId = request.getCourseId();
        List<ModulesRequest> moduleRequests = request.getModules();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new YourCustomException("Course not found"));
        // Получение списка идентификаторов модулей, присутствующих в текущем запросе
        List<Long> moduleRequestIds = moduleRequests.stream()
                .map(ModulesRequest::getId)
                .collect(Collectors.toList());
        // Получение списка модулей, которые нужно удалить
        List<Modules> modulesToDelete = course.getModules().stream()
                .filter(module -> !moduleRequestIds.contains(module.getId()))
                .collect(Collectors.toList());
        // Удаление модулей из списка модулей курса
        course.getModules().removeAll(modulesToDelete);

        // Удаление модулей, которые отсутствуют в текущем запросе, из базы данных
        modulesRepository.deleteAll(modulesToDelete);
        return new Message("delete");
    }
}