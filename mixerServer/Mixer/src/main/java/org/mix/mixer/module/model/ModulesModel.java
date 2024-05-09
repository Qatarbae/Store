package org.mix.mixer.module.model;

public record ModulesModel(
        Long id,
        Double moduleNumber,

        String title,

        String description,

        Integer code,

        Long courseId
) {
}
