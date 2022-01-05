package com.erick.blog.dtos;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<PostDTO> postList;
    private List<CommentDTO> commentList;
    private List<AlbumDTO> albumsList;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, String password, List<PostDTO> postList,
            List<CommentDTO> commentList, List<AlbumDTO> albumsList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.postList = postList;
        this.commentList = commentList;
        this.albumsList = albumsList;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PostDTO> getPostList() {
        return postList;
    }

    public void setPostList(List<PostDTO> postList) {
        this.postList = postList;
    }

    public List<CommentDTO> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentDTO> commentList) {
        this.commentList = commentList;
    }

    public List<AlbumDTO> getAlbumsList() {
        return albumsList;
    }

    public void setAlbumsList(List<AlbumDTO> albumsList) {
        this.albumsList = albumsList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDTO other = (UserDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}