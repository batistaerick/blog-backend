package com.erick.blog.services;

import com.erick.blog.converters.CommentConverter;
import com.erick.blog.dtos.CommentDTO;
import com.erick.blog.entities.Comment;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final CommentConverter converter;
    private final UserService userService;

    @Autowired
    private PostService postService;

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Comment findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new HandlerException("Comment not found!"));
    }

    public Comment save(Long userId, Long postId, CommentDTO commentDTO) {
        Comment comment = converter.dtoToEntity(commentDTO);
        comment.setUser(userService.findById(userId));
        comment.setPost(postService.findById(postId));
        comment.setDate(Instant.now());
        return repository.save(comment);
    }

    public void deleteById(Long commentId, String userEmail) {
        try {
            if (!findById(commentId).getUser().getEmail().equals(userEmail)) {
                throw new HandlerException("Only creator can delete this comment!");
            }
            repository.deleteById(commentId);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

}
