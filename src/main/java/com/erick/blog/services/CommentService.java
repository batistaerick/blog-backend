package com.erick.blog.services;

import com.erick.blog.converters.CommentConverter;
import com.erick.blog.domains.dtos.CommentDTO;
import com.erick.blog.domains.dtos.PostDTO;
import com.erick.blog.domains.dtos.UserDTO;
import com.erick.blog.domains.entities.Comment;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final CommentConverter converter;
    private final UserService userService;
    private final PostService postService;

    public CommentDTO save(Long postId, CommentDTO commentDTO, Authentication authentication) {
        UserDTO userDTO = userService.findByEmail(authentication.getName());
        PostDTO postDTO = postService.findById(postId);
        commentDTO.setUser(userDTO);
        commentDTO.setPost(postDTO);
        commentDTO.setDate(Instant.now());
        Comment comment = converter.dtoToEntity(commentDTO);
        return converter.entityToDto(repository.save(comment));
    }

    public CommentDTO update(CommentDTO commentDTO, Long postId, Authentication authentication) {
        boolean isNotEquals = !findById(commentDTO.getId()).getUser().getEmail().equals(authentication.getName());
        if (isNotEquals) {
            throw new HandlerException("You can only update your comments!");
        }
        PostDTO postDTO = postService.findById(postId);
        UserDTO userDTO = userService.findByEmail(authentication.getName());
        commentDTO.setUser(userDTO);
        commentDTO.setPost(postDTO);
        Comment comment = converter.dtoToEntity(commentDTO);
        return converter.entityToDto(repository.save(comment));
    }

    public Page<CommentDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(converter::entityToDto);
    }

    public CommentDTO findById(Long id) {
        return repository.findById(id).map(converter::entityToDto)
                .orElseThrow(() -> new HandlerException("Comment not found!"));
    }

    public void deleteById(Long id, Authentication authentication) {
        try {
            boolean isNotEquals = !findById(id).getUser().getEmail().equals(authentication.getName());
            if (isNotEquals) {
                throw new HandlerException("Only creator can delete this comment!");
            }
            repository.deleteById(id);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

}
