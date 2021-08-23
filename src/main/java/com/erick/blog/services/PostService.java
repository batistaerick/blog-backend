package com.erick.blog.services;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.erick.blog.entities.Post;
import com.erick.blog.entities.User;
import com.erick.blog.repositories.PostRepository;
import com.erick.blog.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> findAll() {
        List<Post> list = postRepository.findAll();
        return list;
    }

    public Post findById(Long id) {
        Optional<Post> obj = postRepository.findById(id);
        return obj.get();
    }

    public List<Post> findByTittle(String search) {
        List<Post> list = findAll().stream().filter(post -> post.getTitle().contains(search))
                .collect(Collectors.toList());
        return list;
    }

    public Post insertPost(Long userId, Post post, MultipartFile file) {
        if (file != null) {
            try {
                post.setImage(toObjects(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        post.setDate(Instant.now());
        post.setUserPost(userRepository.findById(userId).get());
        post = postRepository.save(post);
        return post;
    }

    private Byte[] toObjects(byte[] bytes) {
        return IntStream.range(0, bytes.length).mapToObj(i -> bytes[i]).toArray(Byte[]::new);
    }

    public void deleteById(Long id, User user) throws Exception {
        if (postRepository.findById(id).get().getUserPost().getLogin().equals(user.getLogin())) {
            postRepository.deleteById(id);
        } else {
            throw new Exception("Only the creator can delete this comment");
        }
        postRepository.deleteById(id);
    }
}