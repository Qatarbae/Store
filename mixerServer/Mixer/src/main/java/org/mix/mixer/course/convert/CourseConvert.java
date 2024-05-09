package org.mix.mixer.course.convert;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CourseConvert<T, S>{
    T toConvert(S model);

    List<T> toListConvert(List<S> model);
}
