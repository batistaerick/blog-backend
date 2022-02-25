package com.erick.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String login;
    private String password;
    private List<PostDTO> postList;
    private List<CommentDTO> commentList;
    private List<AlbumDTO> albumsList;
}