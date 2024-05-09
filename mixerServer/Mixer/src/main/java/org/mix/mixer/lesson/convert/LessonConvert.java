package org.mix.mixer.lesson.convert;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LessonConvert<T, S> {
    T toConvert(S model);

    List<T> toListConvert(List<S> models);
}