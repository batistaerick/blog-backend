package com.erick.blog.services;

import com.erick.blog.converters.PostConverter;
import com.erick.blog.domains.dtos.PostDTO;
import com.erick.blog.domains.dtos.UserDTO;
import com.erick.blog.domains.entities.Post;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final PostConverter converter;
    private final UserService userService;

    public PostDTO save(PostDTO dto, Authentication authentication) {
        UserDTO userDTO = userService.findByEmail(authentication.getName());
        dto.setUser(userDTO);
        dto.setDate(Instant.now());
        Post post = converter.dtoToEntity(dto);
        return converter.entityToDto(repository.save(post));
    }

    public PostDTO update(PostDTO dto, Authentication authentication) {
        boolean isNotEquals = !findById(dto.getId()).getUser().getEmail().equals(authentication.getName());
        if (isNotEquals) {
            throw new HandlerException("You can only update your comments!");
        }
        UserDTO userDTO = userService.findByEmail(authentication.getName());
        dto.setUser(userDTO);
        Post post = converter.dtoToEntity(dto);
        return converter.entityToDto(repository.save(post));
    }

    public Page<PostDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(converter::entityToDto);
    }

    public PostDTO findById(Long id) {
        return repository.findById(id).map(converter::entityToDto)
                .orElseThrow(() -> new HandlerException("Post not found!"));
    }

    public List<PostDTO> findByTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        return repository.findByTitleContainingIgnoreCase(title)
                .stream().map(converter::entityToDto).toList();
    }

    public void deleteById(Long id, Authentication authentication) {
        try {
            boolean isNotEquals = !findById(id).getUser().getEmail().equals(authentication.getName());
            if (isNotEquals) {
                throw new HandlerException("Only creator can delete this comment!");
            }
            repository.deleteById(id);
        } catch (Exception exception) {
            throw new HandlerException(exception);
        }
    }

}
