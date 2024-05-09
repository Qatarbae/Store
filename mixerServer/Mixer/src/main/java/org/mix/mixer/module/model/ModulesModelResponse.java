package org.mix.mixer.module.model;

public record ModulesModelResponse(
        Long id,

        Double moduleNumber,

        String title,

        String description,

        Integer code,

        Long courseId
) {
}
