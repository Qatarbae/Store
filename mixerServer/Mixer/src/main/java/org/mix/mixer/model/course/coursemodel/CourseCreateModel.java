package org.mix.mixer.model.course.coursemodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreateModel {

    private String title;
    private Integer memberCount;
    private Integer price;
    private Integer courseTime;
    private String description;
}