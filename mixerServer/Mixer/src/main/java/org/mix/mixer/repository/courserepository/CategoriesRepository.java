package org.mix.mixer.repository.courserepository;

import org.mix.mixer.entity.course.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    boolean existsByName(String name);
}
