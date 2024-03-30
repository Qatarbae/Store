package org.mix.mixer.convert.userconvert;

public interface UserConvert<T, S> {
    T toConvert(S model);
}

