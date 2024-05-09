package org.mix.mixer.convert.courseconvert;

public interface CourseConvert<T, S>{
    T toConvert(S model);
}
