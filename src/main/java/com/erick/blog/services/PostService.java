package com.erick.blog.services;

import com.erick.blog.dtos.PostDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Post;
import com.erick.blog.exceptions.PostException;
import com.erick.blog.exceptions.UserException;
import com.erick.blog.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final UserService userService;

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PostException("Post not found!"));
    }

    public List<Post> findByTittle(String search) {
        return findAll().stream().filter(post -> post.getTitle().contains(search))
                .collect(Collectors.toList());
    }

    public Post insertPost(Long userId, PostDTO postDTO, String image) {
        Post post = dtoToEntity(postDTO);

        if (image != null) {
            post.setImage(image);
        }
        post.setDate(Instant.now());
        post.setUserPost(userService.findById(userId));

        return repository.save(post);
    }

    public void deleteById(Long id, UserDTO userDTO) {
        try {
            if (!findById(id).getUserPost().getLogin().equals(userDTO.getLogin())) {
                throw new PostException("Only creator can delete this comment!");
            }
            repository.deleteById(id);
        } catch (Exception e) {
            throw new PostException(e.getMessage());
        }
    }

    public Post dtoToEntity(PostDTO dto) {
        try {
            Post entity = new Post();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }
}