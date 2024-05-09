package org.mix.mixer.entity.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mix.mixer.course.entity.Course;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_description")
public class CourseDescription {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "course_id")
    private Course course;

    private String about;

    private String whatWillYouLearn;

    private String forWhom;

    private String initialRequirements;

    private String howIsTheTraining;

    private String whatAreYouGretting;
}