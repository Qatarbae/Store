package org.mix.mixer.entity.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mix.mixer.lesson.entity.Lesson;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "steps")
public class Step {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "stepNumber")
    private Integer stepNumber;

    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private StepType stepType;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}