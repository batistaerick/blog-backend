package com.erick.blog.services;

import com.erick.blog.converters.PostConverter;
import com.erick.blog.dtos.PostDTO;
import com.erick.blog.entities.Post;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final PostConverter converter;
    private final UserService userService;

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new HandlerException("Post not found!"));
    }

    public List<Post> findByTitle(String title) {
        return findAll().stream().filter(post -> post.getTitle().contains(title)).toList();
    }

    public Post save(Long userId, PostDTO postDTO) {
        Post post = converter.dtoToEntity(postDTO);
        post.setDate(Instant.now());
        post.setUser(userService.findById(userId));
        return repository.save(post);
    }

    public void deleteById(Long postId, String userEmail) {
        try {
            if (!findById(postId).getUser().getEmail().equals(userEmail)) {
                throw new HandlerException("Only creator can delete this comment!");
            }
            repository.deleteById(postId);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

}
