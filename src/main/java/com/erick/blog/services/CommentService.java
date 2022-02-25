package com.erick.blog.services;

import com.erick.blog.dtos.CommentDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Comment;
import com.erick.blog.exceptions.CommentException;
import com.erick.blog.exceptions.UserException;
import com.erick.blog.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final UserService userService;
    private final PostService postService;

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Comment findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CommentException("Comment not found!"));
    }

    public Comment insertPost(Long userId, Long postId, CommentDTO commentDTO) {
        Comment comment = dtoToEntity(commentDTO);
        comment.setUserComment(userService.findById(userId));
        comment.setPost(postService.findById(postId));
        comment.setDate(Instant.now());

        return repository.save(comment);
    }

    public void deleteById(Long id, UserDTO userDTO) {
        try {
            if (!findById(id).getUserComment().getLogin().equals(userDTO.getLogin())) {
                throw new CommentException("Only creator can delete this comment!");
            }
            repository.deleteById(id);
        } catch (Exception e) {
            throw new CommentException(e.getMessage());
        }
    }

    public Comment dtoToEntity(CommentDTO dto) {
        try {
            Comment entity = new Comment();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }
}