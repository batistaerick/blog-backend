package com.erick.blog.utils;

import com.erick.blog.domains.dtos.*;
import com.erick.blog.domains.entities.*;

public class DefaultConverters {

    private DefaultConverters() {
        throw new IllegalStateException("Utility class");
    }

    public static RoleDTO roleEntityToDto(Role role) {
        return new RoleDTO(role.getId(), role.getRoleName());
    }

    public static Role roleDtoToEntity(RoleDTO dto) {
        return new Role(dto.getId(), dto.getRoleName());
    }

    public static UserDTO userEntityToDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                null,
                null,
                null,
                null
        );
    }

    public static User userDtoToEntity(UserDTO dto) {
        return new User(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                null,
                null,
                null,
                null
        );
    }

    public static PostDTO postEntityToDto(Post post) {
        return new PostDTO(
                post.getId(),
                post.getDate(),
                post.getTitle(),
                post.getBody(),
                post.getImageUrl(),
                null,
                null
        );
    }

    public static Post postDtoToEntity(PostDTO dto) {
        return new Post(
                dto.getId(),
                dto.getDate(),
                dto.getTitle(),
                dto.getBody(),
                dto.getImageUrl(),
                null,
                null
        );
    }

    public static CommentDTO commentEntityToDto(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getText(),
                comment.getDate(),
                null,
                null
        );
    }

    public static Comment commentDtoToEntity(CommentDTO dto) {
        return new Comment(
                dto.getId(),
                dto.getText(),
                dto.getDate(),
                null,
                null
        );
    }

    public static AlbumDTO albumEntityToDto(Album album) {
        return new AlbumDTO(album.getId(), album.getImageUrl(), null);
    }

    public static Album albumDtoToEntity(AlbumDTO dto) {
        return new Album(dto.getId(), dto.getImageUrl(), null);
    }

}
