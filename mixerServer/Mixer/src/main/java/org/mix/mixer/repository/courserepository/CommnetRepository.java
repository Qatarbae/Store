package org.mix.mixer.repository.courserepository;


import org.mix.mixer.entity.comment.CommentLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommnetRepository extends JpaRepository<CommentLesson, Long> {

    List<CommentLesson> findByLessonId (Long lessonId);

}
