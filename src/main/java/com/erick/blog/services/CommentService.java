package com.erick.blog.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.erick.blog.dtos.CommentDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Comment;
import com.erick.blog.exceptions.DeleteException;
import com.erick.blog.repositories.CommentRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Comment findById(Long id) {
        Optional<Comment> obj = repository.findById(id);
        return obj.isPresent() ? obj.get() : null;
    }

    public Comment insertPost(Long userId, Long postId, CommentDTO commentDTO) {
        Comment comment = new Comment();

        BeanUtils.copyProperties(commentDTO, comment);

        comment.setUserComment(userService.findById(userId));
        comment.setPost(postService.findById(postId));
        comment.setDate(Instant.now());
        comment = repository.save(comment);

        return comment;
    }

    public void deleteById(Long id, UserDTO userDTO) {
        try {
            if (findById(id).getUserComment().getEmail().equals(userDTO.getEmail())) {
                repository.deleteById(id);
            } else {
                throw new DeleteException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}