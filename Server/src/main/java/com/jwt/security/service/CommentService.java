package com.jwt.security.service;

import com.jwt.security.Entity.course.Lesson;
import com.jwt.security.repository.LessonRepository;
import com.jwt.security.Entity.text.Comment;
import com.jwt.security.repository.CommnetRepository;
import com.jwt.security.Entity.user.User;
import com.jwt.security.requestResponse.CommentRequest;
import com.jwt.security.requestResponse.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final LessonRepository lessonRepository;
    private final CommnetRepository commnetRepository;
    private TimeService timeService;
    public CommentResponse addComment(CommentRequest commentRequest, User user){
        Lesson lesson = lessonRepository.findById(commentRequest.getLessonId()).orElseThrow();
        Comment comment = new Comment();
        timeService = new TimeService();
        timeService.setDate(new Timestamp(System.currentTimeMillis()));

        comment.setLesson(lesson);
        comment.setUser(user);
        comment.setText(commentRequest.getText());
        comment.setDate(timeService.getDate());

        Long commentId = commnetRepository.save(comment).getId();

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(commentId);
        commentResponse.setText(commentRequest.getText());
        commentResponse.setLessonId(lesson.getId());
        commentResponse.setUserId(user.getId());
        commentResponse.setUserName(user.getFirstname());
        commentResponse.setDate(timeService.getDate().toString());
        commentResponse.setTimeZone(timeService.getTimeZone().toString());

        return commentResponse;
    }

    public List<CommentResponse> getComments(Long lessonId){
        List<Comment> commentList = commnetRepository.findByLessonId(lessonId);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponses.add(
                    getCommentResponse(comment)
            );
        }
        return commentResponses;
    }

    public CommentResponse getCommentResponse(Comment comment){
        CommentResponse commentResponse = new CommentResponse();

        // информация о комментарии
        commentResponse.setId(comment.getId());
        commentResponse.setText(comment.getText());
        commentResponse.setLessonId(comment.getLesson().getId());
        commentResponse.setUserId(comment.getUser().getId());
        commentResponse.setUserName(comment.getUser().getFirstname());

        // Форматирование даты
        commentResponse.setDate(comment.getDate().toString());

        // часовой пояс
        commentResponse.setTimeZone(timeService.getTimeZone().toString());
        return commentResponse;
    }
}
