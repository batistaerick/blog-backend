package com.erick.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Instant date;
    private String title;
    private String body;
    private String image;
    private UserDTO userPost;
    private List<CommentDTO> commentList;
}