package com.erick.blog.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.erick.blog.entities.Comment;
import com.erick.blog.entities.User;
import com.erick.blog.repositories.CommentRepository;
import com.erick.blog.repositories.PostRepository;
import com.erick.blog.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public List<Comment> findAll() {
        List<Comment> list = commentRepository.findAll();
        return list;
    }

    public Comment findById(Long id) {
        Optional<Comment> obj = commentRepository.findById(id);
        return obj.get();
    }

    public Comment insertPost(Long userId, Long postId, Comment comment) throws NotFoundException {
        comment.setUserComment(userRepository.findById(userId).get());
        comment.setPost(postRepository.findById(postId).get());
        comment.setDate(Instant.now());
        comment = commentRepository.save(comment);
        return comment;
    }

    public void deleteById(Long id, User user) throws Exception {
        if (commentRepository.findById(id).get().getUserComment().getLogin().equals(user.getLogin())) {
            commentRepository.deleteById(id);
        } else {
            throw new Exception("Only the creator can delete this comment");
        }
    }
}