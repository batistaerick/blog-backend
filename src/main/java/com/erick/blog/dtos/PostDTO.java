package com.erick.blog.dtos;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class PostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Instant date;
    private String title;
    private String body;
    private String image;
    private UserDTO userPost;
    private List<CommentDTO> commentList;

    public PostDTO() {
    }

    public PostDTO(Long id, Instant date, String title, String body, String image, UserDTO userPost,
            List<CommentDTO> commentList) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.body = body;
        this.image = image;
        this.userPost = userPost;
        this.commentList = commentList;
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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserDTO getUserPost() {
        return userPost;
    }

    public void setUserPost(UserDTO userPost) {
        this.userPost = userPost;
    }

    public List<CommentDTO> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentDTO> commentList) {
        this.commentList = commentList;
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
        PostDTO other = (PostDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}