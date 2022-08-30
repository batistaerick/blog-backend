package com.erick.blog.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class PostDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Instant date;
    private String title;
    private String body;
    private String imageUrl;
    private UserDTO user;
    private List<CommentDTO> comments;

}
