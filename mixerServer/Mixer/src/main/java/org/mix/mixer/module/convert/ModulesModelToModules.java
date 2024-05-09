package org.mix.mixer.module.convert;

import org.mix.mixer.course.entity.Course;
import org.mix.mixer.course.model.CourseCreateModel;
import org.mix.mixer.module.entity.Modules;
import org.mix.mixer.module.model.ModulesModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModulesModelToModules implements ModulesConvert<Modules, ModulesModel>{

    @Override
    public Modules toConvert(ModulesModel model) {
        return Modules.builder()
                .id(model.id())
                .title(model.title())
                .code(model.code())
                .moduleNumber(model.moduleNumber())
                .description(model.description()).build();
    }

    @Override
    public List<Modules> toListConvert(List<ModulesModel> models) {
        List<Modules> modulesList = new ArrayList<>();
        for (ModulesModel model : models){
            modulesList.add(toConvert(model));
        }
        return modulesList;
    }

    public Modules toConvert(Modules course, ModulesModel model){
        if (course != null) {
            course.setTitle(model.title());
            course.setTitle(model.title());
            course.setCode(model.code());
            course.setDescription(model.description());
            course.setModuleNumber(model.moduleNumber());
        }
        return course;
    }
}
