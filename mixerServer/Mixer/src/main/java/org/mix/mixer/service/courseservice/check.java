//package org.mix.mixer.service.courseservice;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.micrometer.common.util.StringUtils;
//import lombok.RequiredArgsConstructor;
//import org.mix.mixer.course.convert.CourseConvert;
//import org.mix.mixer.entity.appuser.User;
//import org.mix.mixer.entity.course.Categories;
//import org.mix.mixer.course.entity.Course;
//import org.mix.mixer.model.course.coursemodel.CourseCreateModel;
//import org.mix.mixer.repository.courserepository.CategoriesRepository;
//import org.mix.mixer.course.repository.CourseRepository;
//import org.mix.mixer.repository.courserepository.ModulesRepository;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class check {
//
//
//    private final CourseRepository courseRepository;
//    private final CourseConvert<Course, CourseCreateModel> courseCreateModelToCourse;
//    private final ModulesRepository modulesRepository;
//    private final ModulesService modulesService;
//    private final LessonRepository lessonRepository;
//    private final LessonService lessonService;
//
//    private final CategoriesRepository categoriesRepository;
//    private final UserRepository userRepository;
//    private final UserService userService;
//
//    public CourseCreateModel addCourse(
//            CourseCreateModel model,
//            MultipartFile image,
//            MultipartFile video,
//            User user
//    ) {
//        Categories categories = categoriesRepository.findById(1l).orElseThrow();
//        Course course = courseCreateModelToCourse.toConvert(model);
//        course.setCategories(categories);
//        course.setCourseCreator(user.getCourseCreator());
//        course.setImage(image.getOriginalFilename());
//        course.setVideo(video.getOriginalFilename());
//        courseRepository.save(course);
//        return request;
//    }
//
//    public NewCourseResponse newCourse(String request, User user) throws Exception {
//        Long courseId;
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(request);
//        String title = jsonNode.get("title").asText();
//        if (user.getCourseCreator() == null) {
//            user = userService.saveUserCreator(user);
//        }
//
//        Course course = new Course();
//        course.setTitle(title);
//        course.setCourseCreator(user.getCourseCreator());
//        try {
//            courseId = courseRepository.save(course).getId();
//        } catch (DataIntegrityViolationException ex) {
//            throw new DataIntegrityViolationException("Такой курс уже есть");
//        }
//
//        return new NewCourseResponse(courseId, course.getTitle(), user.getFirstname(), user.getLastname());
//    }
//
//
//    public Course generateCourse(CourseRequest request, MultipartFile image, MultipartFile video, User user) {
//
//        User existingUser = userRepository.findById(user.getId()).orElseThrow();
//        Categories categories = categoriesRepository.findById(1l).orElseThrow();
//
//        Course course = new Course();
//        course.setTitle(request.getTitle());
//        course.setCourseCreator(existingUser.getCourseCreator());
//        course.setMemberCount(request.getMemberCount());
//        course.setCategories(categories);
//        course.setPrice(request.getPrice());
//        course.setCourseTime(request.getCourseTime());
//        course.setImage(image.getOriginalFilename());
//        course.setVideo(video.getOriginalFilename());
//        course.setDescription(request.getDescription());
//        return course;
//    }
//
//    public ResponseEntity<List<CourseResponse>> allCourse() {
//        List<Course> courses = courseRepository.findAll();
//
//        List<CourseResponse> courseResponses = new ArrayList<>();
//        for (Course course : courses) {
//            courseResponses.add(courseResponse(course));
//        }
//
//        return new ResponseEntity<>(courseResponses, HttpStatus.OK);
//    }
//
//    public ResponseEntity<List<CourseResponse>> allCourseCreator(User user) {
//        List<Course> courses = courseRepository.findByCourseCreatorId(user.getCourseCreator().getId());
//        List<CourseResponse> courseResponses = new ArrayList<>();
//        for (Course course : courses) {
//            courseResponses.add(courseResponse(course));
//        }
//        return new ResponseEntity<>(courseResponses, HttpStatus.OK);
//    }
//
//
//    public FullCourseResponse fullCourse(FullCourseRequest request) {
//        long courseId = request.getCourseId();
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new CustomException("Course not found"));
//
//        // Удаление всех текущих модулей из курса
//        course.getModules().removeAll(course.getModules());
//
//        // Обновление модулей курса согласно данным из запроса и установка нового списка модулей
//        course.setModules(modulesService.updateModules(course, request.getModules()));
//
//        // Обновление уроков курса согласно данным из запроса
//        lessonService.updateLessons(course, request.getLessons());
//
//        modulesRepository.saveAll(course.getModules());
//        courseRepository.save(course);
//        return getFullCourse(courseId);
//    }
//
//
//    public FullCourseResponse getFullCourse(Long courseId) {
//        // Вызов сервисного метода для сохранения курса
//        FullCourseResponse fullCourseResponse = new FullCourseResponse();
//
//        // Получение курса по courseId или выброс исключения, если курс не найден
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new CustomException("Course not found"));
//
//        fullCourseResponse.setCourseId(courseId);
//
//        List<ModulesResponse> modulesResponses = new ArrayList<>();
//        List<LessonResponse> lessonResponses = new ArrayList<>();
//
//        for (Modules modules : course.getModules()) {
//            ModulesResponse modulesResponse = modulesService.getModulesResponse(modules);
//            modulesResponses.add(modulesResponse);
//
//            if (!modules.getLessons().isEmpty()) {
//                for (Lesson lesson : modules.getLessons()) {
//                    LessonResponse lessonResponse = lessonService.getLessonResponse(lesson, modules.getId());
//                    lessonResponses.add(lessonResponse);
//                }
//            }
//        }
//
//        fullCourseResponse.setModules(modulesResponses);
//        fullCourseResponse.setLessons(lessonResponses);
//
//        return fullCourseResponse;
//    }
//
//    public List<CourseResponse> searchCourses(String title, boolean hasCertificate, boolean isFree, int maxDistance) {
//        if (StringUtils.isEmpty(title)) {
//            // Если заданная часть названия пуста, вернуть 8 случайных курсов
//            int limit = 8;
//            int totalCourseCount = courseRepository.getTotalCourseCount();
//            int randomCourseLimit = Math.min(totalCourseCount, limit);
//
//            List<Course> randomCourses = courseRepository.findRandomCourses(randomCourseLimit);
//            List<CourseResponse> fullCourseResponses = new ArrayList<>();
//
//            for (Course course : randomCourses) {
//                CourseResponse courseResponse = courseResponse(course);
//                fullCourseResponses.add(courseResponse);
//            }
//            return fullCourseResponses;
//        } else {
//            List<Course> courses = courseRepository.findByTitleLevenshteinDistance(title, maxDistance);
//
////            // Фильтровать курсы по наличию сертификата и стоимости
////            courses = courses.stream()
////                    .filter(course -> course.isHasCertificate() == hasCertificate)
////                    .filter(course -> course.isFree() == isFree)
////                    .collect(Collectors.toList());
//            List<CourseResponse> fullCourseResponses = new ArrayList<>();
//            for (Course course : courses) {
//                CourseResponse courseResponse = courseResponse(course);
//                fullCourseResponses.add(courseResponse);
//            }
//            return fullCourseResponses;
//        }
//    }
//
//    public List<CategoriesResponse> getCategories() {
//        List<Categories> categoriesResponseList = categoriesRepository.findAll();
//        List<CategoriesResponse> categoriesResponseArrayList = new ArrayList<>();
//        for (Categories categories : categoriesResponseList) {
//            CategoriesResponse categoriesResponse = new CategoriesResponse();
//            categoriesResponse.setId(categories.getId());
//            categoriesResponse.setName(categories.getName());
//            categoriesResponseArrayList.add(categoriesResponse);
//        }
//        return categoriesResponseArrayList;
//    }
//
//    public CourseResponse courseResponse(Course course) {
//        CourseResponse courseResponse = new CourseResponse();
//        courseResponse.setId(course.getId());
//        courseResponse.setTitle(course.getTitle());
//        courseResponse.setMemberCount(course.getMemberCount());
//        courseResponse.setPrice(course.getPrice());
//        courseResponse.setCourseTime(course.getCourseTime());
//        courseResponse.setImage(course.getImage());
//        courseResponse.setVideo(course.getVideo());
//        courseResponse.setDescription(course.getDescription());
//        return courseResponse;
//    }
//
//
//}