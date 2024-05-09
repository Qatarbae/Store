package org.mix.mixer.module.convert;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ModulesConvert<T, S>{
    T toConvert(S model);

    List<T> toListConvert(List<S> models);
}