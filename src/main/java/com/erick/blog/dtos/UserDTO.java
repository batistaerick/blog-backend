package com.erick.blog.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<RoleDTO> roles;
    private List<PostDTO> posts;
    private List<CommentDTO> comments;
    private List<AlbumDTO> albums;

}