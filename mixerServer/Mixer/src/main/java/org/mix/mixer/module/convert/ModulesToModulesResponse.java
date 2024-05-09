package org.mix.mixer.module.convert;

import org.mix.mixer.module.entity.Modules;
import org.mix.mixer.module.model.ModulesModel;
import org.mix.mixer.module.model.ModulesModelResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModulesToModulesResponse implements ModulesConvert<ModulesModel, Modules>{

    @Override
    public ModulesModel toConvert(Modules model) {
        return new ModulesModel(
                model.getId(),
                model.getModuleNumber(),
                model.getTitle(),
                model.getDescription(),
                model.getCode(),
                model.getCourse().getId()
        );
    }

    @Override
    public List<ModulesModel> toListConvert(List<Modules> models) {
        List<ModulesModel> modulesModels = new ArrayList<>();
        for(Modules modules: models){
            modulesModels.add(toConvert(modules));
        }
        return modulesModels;
    }
}
