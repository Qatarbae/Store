package org.mix.mixer.entity.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mix.mixer.course.entity.Course;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "modules")
public class Modules {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "module_number")
    private Double moduleNumber;

    private String title;
    private String description;

    private Integer code;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "modules", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;

}