package com.erick.blog.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.erick.blog.dtos.PostDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Post;
import com.erick.blog.exceptions.DeleteException;
import com.erick.blog.repositories.PostRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private UserService userService;

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(Long id) {
        Optional<Post> obj = repository.findById(id);
        return obj.isPresent() ? obj.get() : null;
    }

    public List<Post> findByTittle(String search) {
        return findAll().stream().filter(post -> post.getTitle().contains(search))
                .collect(Collectors.toList());
    }

    public Post insertPost(Long userId, PostDTO postDTO, String image) {
        Post post = new Post();
        BeanUtils.copyProperties(postDTO, post);

        if (image != null) {
            post.setImage(image);
        }
        post.setDate(Instant.now());
        post.setUserPost(userService.findById(userId));
        post = repository.save(post);

        return post;
    }

    public void deleteById(Long id, UserDTO userDTO) {
        try {
            if (findById(id).getUserPost().getLogin().equals(userDTO.getLogin())) {
                repository.deleteById(id);
            } else {
                throw new DeleteException();
            }
            repository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}